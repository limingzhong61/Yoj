package com.yoj.custom.judge;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.yoj.custom.judge.bean.ExecuteMessage;
import com.yoj.custom.judge.bean.JudgeSource;
import com.yoj.custom.judge.bean.TestResult;
import com.yoj.custom.judge.enums.JudgeResult;
import com.yoj.custom.judge.enums.Language;
import com.yoj.custom.judge.threads.JudgeThreadPoolManager;
import com.yoj.custom.judge.util.ExecutorUtil;
import com.yoj.custom.properties.JudgeProperties;
import com.yoj.web.pojo.Solution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class Judge {
//    private final String[] fileNames = {"main.c", "main.cpp", "Main.java", "main.py"};

    @Autowired
    private JudgeProperties judgeProperties;
    @Autowired
    private ExecutorUtil executor;

    @Autowired
    JudgeThreadPoolManager judgeThreadPoolManager;

//    public void judge(){
//        //模拟的随机数
//        String orderNo = System.currentTimeMillis() + UUID.randomUUID().toString();
//        judgeThreadPoolManager.addTask(orderNo);
//    }

    /**
     * return update solution by judgeSource
     * @param judgeSource
     * @return update solution
     */
    public Solution judge(JudgeSource judgeSource) {
        // linux path,tmp directory store temporary files
        //uuid 重复的可能性很低
        String dirPath = UUID.randomUUID().toString();
        String linuxPath = judgeProperties.getLinux().getSolutionFilePath()  + dirPath;
        // windows path,
        String windowsPath = judgeProperties.getWindows().getSolutionFilePath() + dirPath;
        Solution solution = new Solution();
        // attribute mapping
        BeanUtils.copyProperties(judgeSource,solution);
        try {
            createSolutionFile(solution, linuxPath, windowsPath);
        } catch (Exception e) {
            e.printStackTrace();
            solution.setErrorMessage("system exception:create file fail");
            solution.setResult(JudgeResult.SYSTEM_ERROR.ordinal());
            log.info("JudgeUtil : create file fail");
            deleteSolutionFile(linuxPath, windowsPath);
            return null;
        }

        // compile the source
        String message = compile(solution.getLanguage(), linuxPath);
        if (message != null) {
            solution.setResult(JudgeResult.COMPILE_ERROR.ordinal());
            solution.setErrorMessage(message);
            log.warn("JudgeUtil : compile error");
            log.warn("JudgeUtil :  " + message);
            deleteSolutionFile(linuxPath, windowsPath);
            return null;
        }
        // chmod -R 755 path
        executor.execute("chmod -R 755 " + linuxPath);
        // judge
        String process = process(solution.getLanguage(), linuxPath);
//		String judge_data = PropertiesUtil.StringValue("judge_data") + "/" + task.getProblemId();
//		String cmd = "python " + PropertiesUtil.StringValue("judge_script") + " " + process + " " + judge_data + " "
//				+ path + " " + task.getTimeLimit() + " " + task.getMemoryLimit();
        String judgeDataPath = judgeProperties.getLinux().getProblemFilePath() + solution.getProblemId();
        String judgePyPath = judgeProperties.getJudgeScriptPath();
        int memoryLimit = judgeSource.getMemoryLimit() * 1024;
        //#服务器内存不够分配。。。。。给大点，和小一点都行????
        if (solution.getLanguage() == Language.JAVA.ordinal()) {
            memoryLimit = 2000000;
        }
        String cmd = "python " + judgePyPath + " " + process + " " + judgeDataPath + " "
                + linuxPath + " " + judgeSource.getTimeLimit() + " " + memoryLimit;
//        String cmd = "python " + "/home/nicolas/judge/judge1.py" + " " + process + " " + judge_data + " "
//                + linuxPath + " " + 1000 + " " + 20000;
        parseToResult(cmd, solution);
        deleteSolutionFile(linuxPath, windowsPath);
        log.info(solution.toString());
        return solution;
    }

    public abstract void createSolutionFile(Solution solution, String linuxPath, String windowsPath) throws Exception;

    public abstract void deleteSolutionFile(String linuxPath, String windowsPath);

    private String compile(int compilerId, String path) {
        /**
         * '0': 'gcc','1' 'g++', '2': 'java', '3': 'python', '4': 'pascal', -o outfile
         */
        String cmd = "";
        switch (compilerId) {
            case 0:
                cmd = "gcc " + path + "/main.c -o " + path + "/main";
                break;
            case 1:
                cmd = "g++ " + path + "/main.cpp -o " + path + "/main";
                break;
            case 2:
                cmd = "javac " + path + "/Main.java";
                break;
            case 3:
                cmd = "python3 -m py_compile " + path + "/main.py";
                break;
//            case 4:
//                cmd = "fpc " + path + "/main.pas -O2 -Co -Ct -Ci";
//                break;
        }
        return executor.execute(cmd).getError();
    }

    private String process(int language, String path) {
        switch (language) {
            case 0:
                return path + "/main";
            case 1:
                return path + "/main";
            case 2:
                return "javalmz-classpathlmz" + path + "lmzMain";
            case 3:
//                                #python编译生成对应的版本文件名字
//                    python_cacheName=main.cpython-36.pyc
                return "python3lmz" + path + "/__pycache__/" + "main.cpython-36.pyc";
//		case 5:
//			return path + "/main";
        }
        return null;
    }

    private void parseToResult(String cmd,Solution solution) {
        ExecuteMessage exec = executor.execute(cmd);
        if (exec.getError() != null) {
            solution.setErrorMessage(exec.getError());
            solution.setResult(JudgeResult.SYSTEM_ERROR.ordinal());
            log.error("=====error====" + solution.getSolutionId() + exec.getStdout() + "    :" + exec.getError());
        } else {
//			Stdout out = JSON.parseObject(exec.getStdout(), Stdout.class);
            try {
                log.info("=====stdout====" + exec.getStdout());
                String jsonFormat = "[" + exec.getStdout() + "]";
                List<TestResult> outs = JSONArray.parseArray(jsonFormat, TestResult.class);
                String testResult = JSONArray.toJSON(outs).toString();
                //必须要保存标准格式的json数据
                // remove last because it's a information that compares with all test results
                solution.setTestResult(JSON.toJSON(outs.subList(0,outs.size()-1)).toString());
                solution.setRuntime(outs.get(outs.size() - 1).getTimeUsed());
                solution.setMemory(outs.get(outs.size() - 1).getMemoryUsed());
                solution.setResult(outs.get(outs.size() - 1).getResult());
            } catch (Exception e) {
                solution.setResult(JudgeResult.SYSTEM_ERROR.ordinal());
                e.printStackTrace();
            }
        }
    }


}
