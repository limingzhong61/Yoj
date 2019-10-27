//package com.yoj.nuts.judge;
//
//import com.alibaba.fastjson.JSONArray;
//import com.yoj.nuts.judge.bean.ExecMessage;
//import com.yoj.nuts.judge.bean.TestResult;
//import com.yoj.nuts.judge.bean.static_fianl.Languages;
//import com.yoj.nuts.judge.bean.static_fianl.Results;
//import com.yoj.nuts.judge.util.ExecutorUtil;
//import com.yoj.nuts.judge.util.SSH2Util;
//import com.yoj.nuts.properties.JudgeProperties;
//import com.yoj.web.bean.Problem;
//import com.yoj.web.bean.Solution;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.UUID;
//
//@Setter
//@Slf4j
//public class elderJudge {
//    private String[] fileNames = {"main.c", "main.cpp", "Main.java", "main.py"};
//
//    @Autowired
//    private JudgeProperties judgeProperties;
//    @Autowired
//    private ExecutorUtil executor;
//
//    public void judge(Solution solution, Problem problem) {
//        // linux path,tmp directory store temporary files
//        //uuid 重复的可能性很低
//        String dirPath = UUID.randomUUID().toString();
//        String linuxPath = judgeProperties.getLinux().getSolutionFilePath()  + dirPath;
//        // windows path,
//        String windowsPath = judgeProperties.getWindows().getSolutionFilePath() + dirPath;
//        try {
//            createSolutionFile(solution, linuxPath, windowsPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//            solution.setErrorMessage("system exception:create file fail");
//            solution.setResult(Results.SystemError);
//            log.info("JudgeUtil : create file fail");
//            deleteSolutionFile(linuxPath, windowsPath);
//            return;
//        }
//
//        // compile the source
//        String message = compile(solution.getLanguage(), linuxPath);
////		if (message != null && task.getCompilerId() != 4) {
//        if (message != null) {
//            solution.setResult(Results.CompileError);
//            solution.setErrorMessage(message);
//            log.warn("JudgeUtil : compile error");
//            log.warn("JudgeUtil :  " + message);
//            deleteSolutionFile(linuxPath, windowsPath);
//            return;
//        }
//        // chmod -R 755 path
//        executor.execute("chmod -R 755 " + linuxPath);
//        // judge
//        String process = process(solution.getLanguage(), linuxPath);
////		String judge_data = PropertiesUtil.StringValue("judge_data") + "/" + task.getProblemId();
////		String cmd = "python " + PropertiesUtil.StringValue("judge_script") + " " + process + " " + judge_data + " "
////				+ path + " " + task.getTimeLimit() + " " + task.getMemoryLimit();
//        String path = linuxPath + "/" + fileNames[solution.getLanguage()];
//        String judgeData = judgeProperties.getLinux().getProblemFilePath() + problem.getProblemId();
//        String judgePyPath = judgeProperties.getJudgeScriptPath();
//        int memoryLimit = problem.getMemoryLimit() * 1024;
//        //#服务器内存不够分配。。。。。给大点，和小一点都行????
//        if (solution.getLanguage() == Languages.JAVA) {
//            memoryLimit = 2000000;
//        }
//        String cmd = "python " + judgePyPath + " " + process + " " + judgeData + " "
//                + linuxPath + " " + problem.getTimeLimit() + " " + memoryLimit;
////        String cmd = "python " + "/home/nicolas/judge/judge1.py" + " " + process + " " + judge_data + " "
////                + linuxPath + " " + 1000 + " " + 20000;
//        parseToResult(cmd, solution);
//        deleteSolutionFile(linuxPath, windowsPath);
//        System.out.println(solution);
//    }
//
//    private void createSolutionFile(Solution solution, String linuxPath, String windowsPath) throws Exception{
//        //create solutionFile();
//        if ("linux".equals(judgeProperties.getPlatform())) {
//            File file = new File(linuxPath);
//            file.mkdirs();
//            FileUtils.write(new File(linuxPath + "/" + fileNames[solution.getLanguage()]),
//                    solution.getCode(), "utf-8");
//        } else {
//            // windows 环境
//            File file = new File(windowsPath);
//            file.mkdirs();
//            FileUtils.write(new File(windowsPath + "/" + fileNames[solution.getLanguage()]),
//                    solution.getCode(), "utf-8");
//            SSH2Util ssh2Util = new SSH2Util(judgeProperties.getIp(), judgeProperties.getUserName(), judgeProperties.getPassword(), 22);
//            ssh2Util.putFile(windowsPath, fileNames[solution.getLanguage()], linuxPath);
//        }
//    }
//
//    private void deleteSolutionFile(String linuxPath, String windowsPath) {
//        executor.execute("rm -rf " + linuxPath);
//        if (!"linux".equals(judgeProperties.getPlatform())) {
//            try {
//                FileUtils.deleteDirectory(new File(windowsPath));
//            } catch (IOException ee) {
//                ee.printStackTrace();
//            }
//        }
//    }
//
//    private String compile(int compilerId, String path) {
//        /**
//         * '0': 'gcc','1' 'g++', '2': 'java', '3': 'python', '4': 'pascal', -o outfile
//         */
//        String cmd = "";
//        switch (compilerId) {
//            case 0:
//                cmd = "gcc " + path + "/main.c -o " + path + "/main";
//                break;
//            case 1:
//                cmd = "g++ " + path + "/main.cpp -o " + path + "/main";
//                break;
//            case 2:
//                cmd = "javac " + path + "/Main.java";
//                break;
//            case 3:
//                cmd = "python3 -m py_compile " + path + "/main.py";
//                break;
////            case 4:
////                cmd = "fpc " + path + "/main.pas -O2 -Co -Ct -Ci";
////                break;
//        }
//        return executor.execute(cmd).getError();
//    }
//
//    private static String process(int compileId, String path) {
//        switch (compileId) {
//            case 0:
//                return path + "/main";
//            case 1:
//                return path + "/main";
//            case 2:
//                return "javalmz-classpathlmz" + path + "lmzMain";
//            case 3:
////                                #python编译生成对应的版本文件名字
////                    python_cacheName=main.cpython-36.pyc
//                return "python3lmz" + path + "/__pycache__/" + "main.cpython-36.pyc";
////		case 5:
////			return path + "/main";
//        }
//        return null;
//    }
//
//    private void parseToResult(String cmd, Solution solution) {
//        ExecMessage exec = executor.execute(cmd);
//        if (exec.getError() != null) {
//            solution.setErrorMessage(exec.getError());
//            solution.setResult(Results.SystemError);
//            log.error("=====error====" + solution.getSolutionId() + exec.getStdout() + "    :" + exec.getError());
//        } else {
////			Stdout out = JSON.parseObject(exec.getStdout(), Stdout.class);
//            try {
//                System.out.println("=====stdout====" + exec.getStdout());
//                String jsonFormat = "[" + exec.getStdout() + "]";
//
//                List<TestResult> outs = JSONArray.parseArray(jsonFormat, TestResult.class);
//                String testResult = JSONArray.toJSON(outs).toString();
//                //必须要保存标准格式的json数据
//                solution.setTestResult(testResult);
//                // log.info("=====stdout====" + out);
//                solution.setRuntime(outs.get(outs.size() - 1).getTimeUsed());
//                solution.setMemory(outs.get(outs.size() - 1).getMemoryUsed());
//                solution.setResult(outs.get(outs.size() - 1).getResult());
//                solution.setTestResults(outs);
//            } catch (Exception e) {
//                solution.setResult(Results.SystemError);
//                e.printStackTrace();
//            }
//        }
//    }
//}
