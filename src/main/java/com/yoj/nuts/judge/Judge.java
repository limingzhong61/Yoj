package com.yoj.nuts.judge;

import com.alibaba.fastjson.JSONArray;
import com.yoj.nuts.judge.bean.ExecMessage;
import com.yoj.nuts.judge.bean.TestResult;
import com.yoj.nuts.judge.bean.static_fianl.Results;
import com.yoj.nuts.judge.utils.ExecutorUtil;
import com.yoj.nuts.judge.utils.PropertiesUtil;
import com.yoj.nuts.judge.utils.SSH2Util;
import com.yoj.web.bean.Problem;
import com.yoj.web.bean.Solution;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

@Setter
@Component
public class Judge {
    private String[] fileNames = {"main.c", "main.cpp", "Main.java", "main.py"};

    @Autowired
//    @Qualifier("remoteExecutor")
    @Qualifier("localExecutor")
    private ExecutorUtil executor;

    public void judge(Solution solution, Problem problem) {
//    	String path = "/opt" + "/" + task.getSubmitId();
        // opt authority not enough to normal user;
        // linux path,tmp directory store temporary files
        String linuxPath = PropertiesUtil.get("linux.solutionFilePath") + problem.getProblemId();
        // windows path,
        String windowsPath = PropertiesUtil.get("windows.solutionFilePath")+ problem.getProblemId();
//        System.out.println(linuxPath);
//        File file = new File(solutionPath);
//        file.mkdirs();
        try {
            if("linux".equals(PropertiesUtil.get("platform"))){
                File file = new File(linuxPath);
                file.mkdirs();
                createFile(solution.getLanguage(), linuxPath, solution.getCode());
            }else{
                // window 环境
                File file = new File(windowsPath);
                file.mkdirs();
                createFile(solution.getLanguage(), windowsPath, solution.getCode());
                SSH2Util ssh2Util = new SSH2Util(PropertiesUtil.get("ip"), PropertiesUtil.get("userName"), PropertiesUtil.get("password"), 22);
                ssh2Util.putFile(windowsPath, fileNames[solution.getLanguage()], linuxPath);
            }

        } catch (Exception e) {
            e.printStackTrace();
            solution.setErrorMessage("system exception:create file fail");
            solution.setResult(Results.SystemError);
            System.out.println("create file fail");
            executor.execute("rm -rf " + linuxPath);
            return;
        }

        // compile the source
        String message = compile(solution.getLanguage(), linuxPath);
//		if (message != null && task.getCompilerId() != 4) {
        if (message != null) {
            solution.setResult(Results.CompileError);
            solution.setErrorMessage(message);
            System.out.println(message);
            executor.execute("rm -rf " + linuxPath);
            System.out.println("compile error");
            return;
        }
        // chmod -R 755 path
        executor.execute("chmod -R 755 " + linuxPath);
        // judge
        String process = process(solution.getLanguage(), linuxPath);
//		String judge_data = PropertiesUtil.StringValue("judge_data") + "/" + task.getProblemId();
//		String cmd = "python " + PropertiesUtil.StringValue("judge_script") + " " + process + " " + judge_data + " "
//				+ path + " " + task.getTimeLimit() + " " + task.getMemoryLimit();
        String path = linuxPath + "/" + fileNames[solution.getLanguage()];
        String judgeData = "/tmp/testData/"+ problem.getProblemId();
        String judgePyPath = "/home/ubuntu/judge/judge1.py";
//        String[] wzies = process.split("wzy");
//        for(String s : wzies){
//            System.out.println(s);
//        }
        String cmd = "python " + judgePyPath + " " + process + " " + judgeData + " "
                + linuxPath + " " + problem.getTimeLimit()+ " " + problem.getMemoryLimit()*1024;
//        String cmd = "python " + "/home/nicolas/judge/judge1.py" + " " + process + " " + judge_data + " "
//                + linuxPath + " " + 1000 + " " + 20000;
//        String cmd = "python /home/nicolas/judge/lmzJudge.py " + linuxPath
//                + "/" + fileNames[solution.getLanguage()] + " /home/nicolas/judge/demo/testdata 3";
        parseToResult(cmd, solution);
        executor.execute("rm -rf " + linuxPath);
        System.out.println(solution);
    }

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
                //liunx
//                cmd = "javac " + path + "/Main.java";
                //windows
                cmd = ". /etc/profile; javac " + path + "/Main.java";
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

    private void createFile(int compilerId, String path, String source) throws Exception {
//        String filename = "";
//        switch (compilerId) {
//            case 0:
//                filename = "main.c";
//                break;
//            case 1:
//                filename = "main.cpp";
//                break;
//            case 2:
//                filename = "Main.java";
//                break;
//            case 3:
//                filename = "main.py";
//                break;
//            case 5:
//                filename = "main.pas";
//                break;
//        }
        File file = new File(path + "/" + fileNames[compilerId]);
        file.createNewFile();
        OutputStream output = new FileOutputStream(file);
        PrintWriter writer = new PrintWriter(output);
        writer.print(source);
        writer.close();
        output.close();
    }

    private static String process(int compileId, String path) {
        switch (compileId) {
            case 0:
                return path + "/main";
            case 1:
                return path + "/main";
            case 2:
//                return "javawzy-classpathwzy" + path + "wzyMain";
                return "\".wzy/etc/profile;wzyjavawzy-classpathwzy" + path + "wzyMain\"";
            case 3:
//                                #python编译生成对应的版本文件名字
//                    python_cacheName=main.cpython-36.pyc
            return "python3wzy" + path + "/__pycache__/" + "main.cpython-36.pyc";
//		case 5:
//			return path + "/main";
        }
        return null;
    }

    private void parseToResult(String cmd, Solution solution) {
        ExecMessage exec = executor.execute(cmd);
        if (exec.getError() != null) {
            solution.setErrorMessage(exec.getError());
            solution.setResult(Results.SystemError);
            System.out.println("=====error====" + solution.getSolutionId() + exec.getStdout() + "    :" + exec.getError());
            // log.error("=====error====" + result.getSubmitId() + ":" + exec.getError());
        } else {
//			Stdout out = JSON.parseObject(exec.getStdout(), Stdout.class);
            System.out.println("=====stdout====" + exec.getStdout());
            String jsonFormat = "[" + exec.getStdout() + "]";
            List<TestResult> outs = JSONArray.parseArray(jsonFormat, TestResult.class);
            // log.info("=====stdout====" + out);
            solution.setRuntime(outs.get(outs.size()-1).getTimeUsed());
            solution.setMemory(outs.get(outs.size()-1).getMemoryUsed());
            solution.setResult(outs.get(outs.size()-1).getResult());
            solution.setTestResults(outs);
        }
    }
}
