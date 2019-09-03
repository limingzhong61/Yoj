package com.yoj.judge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONArray;
import com.yoj.judge.bean.ExecMessage;
import com.yoj.judge.bean.TestResult;
import com.yoj.judge.utils.ExecutorUtil;
import com.yoj.judge.utils.SSH2Util;
import com.yoj.judge.utils.impl.RemoteExecutor;
import com.yoj.web.bean.Solution;
import com.yoj.web.bean.User;

@Component
public class Judge {

//	@Qualifier("remoteExcutor")
//	@Autowired
	private ExecutorUtil executor = new RemoteExecutor();

	public void judge(Solution solution) {
//    	String path = "/opt" + "/" + task.getSubmitId();
		// opt authority not enough to normal user;

		// linux path,tmp directory store temporary files
		String linuxPath = "/tmp" + "/" + 1;
		// windows path,
		String windowsPath = "E://tmp//1";

		System.out.println(linuxPath);
		File file = new File(windowsPath);
		file.mkdirs();
		try {
//			createFile(task.getCompilerId(), path, task.getSource());
			createFile(1, windowsPath, solution.getCode());

			// window 环境
			SSH2Util ssh2Util = new SSH2Util("47.103.195.173", "nicolas", "nicolas", 22);
			ssh2Util.putFile(windowsPath, "main.c", linuxPath);
		} catch (Exception e) {
			e.printStackTrace();
			solution.setErrorMessage("system exception:create file fail");
			System.out.println("create file fail");
			executor.execute("rm -rf " + linuxPath);
		}
		// compile the source
//		String message = complie(task.getCompilerId(), path);
		String message = complie(1, linuxPath);
//		if (message != null && task.getCompilerId() != 4) {
		if (message != null) {
			solution.setResult("compile error");
			solution.setErrorMessage(message);
			System.out.println(message);
			executor.execute("rm -rf " + linuxPath);
			System.out.println("compile error");
		}
		// chmod -R 755 path
		executor.execute("chmod -R 755 " + linuxPath);
		// judge
//		String process = process(task.getCompilerId(), path);
//		String process = process(1, path);
//		String judge_data = PropertiesUtil.StringValue("judge_data") + "/" + task.getProblemId();
//		String cmd = "python " + PropertiesUtil.StringValue("judge_script") + " " + process + " " + judge_data + " "
//				+ path + " " + task.getTimeLimit() + " " + task.getMemoryLimit();
		String cmd = "python /home/nicolas/Lo-runner-master/demo/test.py " + linuxPath
				+ "/main.c /home/nicolas/Lo-runner-master/demo/testdata 3";
		parseToResult(cmd, solution);
		executor.execute("rm -rf " + linuxPath);
//		results.add(result);
		System.out.println(solution);
	}

	private String complie(int compilerId, String path) {
		/**
		 * '1': 'gcc','g++', '3': 'java', '4': 'pascal', '5': 'python', -o outfile
		 */
		String cmd = "";
		switch (compilerId) {
		case 1:
			cmd = "gcc " + path + "/main.c -o " + path + "/main";
			break;
		case 2:
			cmd = "g++ " + path + "/main.cpp -o " + path + "/main";
			break;
		case 3:
			cmd = "javac " + path + "/Main.java";
			break;
		case 4:
			cmd = "fpc " + path + "/main.pas -O2 -Co -Ct -Ci";
			break;
		case 5:
			cmd = "python3 -m py_compile " + path + "/main.py";
			break;
		}
		return executor.execute(cmd).getError();
	}

	private void createFile(int compilerId, String path, String source) throws Exception {
		String filename = "";
		switch (compilerId) {
		case 1:
			filename = "main.c";
			break;
		case 2:
			filename = "main.cpp";
			break;
		case 3:
			filename = "Main.java";
			break;
		case 4:
			filename = "main.pas";
			break;
		case 5:
			filename = "main.py";
			break;
		}
		File file = new File(path + "/" + filename);
		file.createNewFile();
		OutputStream output = new FileOutputStream(file);
		PrintWriter writer = new PrintWriter(output);
		writer.print(source);
		writer.close();
		output.close();
	}

//	private static String process(int compileId, String path) {
//		switch (compileId) {
//		case 1:
//			return path + "/main";
//		case 2:
//			return path + "/main";
//		case 3:
//			return "javawzy-classpathwzy" + path + "wzyMain";
//		case 4:
//			return path + "/main";
////		case 5:
////			return "python3wzy" + path + "/__pycache__/" + PropertiesUtil.StringValue("python_cacheName");
//		}
//		return null;
//	}

	private void parseToResult(String cmd, Solution solution) {
		ExecMessage exec = executor.execute(cmd);
		if (exec.getError() != null) {
			solution.setErrorMessage(exec.getError());
			System.out.println("=====error====" + solution.getSolutionId() + ":" + exec.getError());
			// log.error("=====error====" + result.getSubmitId() + ":" + exec.getError());
		} else {
//			Stdout out = JSON.parseObject(exec.getStdout(), Stdout.class);
			String jsonFormat = "[" + exec.getStdout() + "]";
			List<TestResult> outs = JSONArray.parseArray(jsonFormat, TestResult.class);
			System.out.println("=====stdout====" + exec.getStdout());
			// log.info("=====stdout====" + out);
			// test set
			Integer timeUsed = 0;
			Integer memoryUsed = 0;
			List<TestResult> tests = new ArrayList<>();
			for (TestResult out : outs) {
				TestResult test = new TestResult();
				test.setResult(out.getResult());
				test.setTimeUsed(out.getTimeUsed());
				test.setMemoryUsed(out.getMemoryUsed());
//				===============         not set errot level         ==============
				if (!"Accepted".equals(test.getResult())) {
					solution.setResult(test.getResult());
					continue;
				}
				tests.add(test);
				timeUsed = Math.max(timeUsed, test.getTimeUsed());
				memoryUsed = Math.max(memoryUsed, test.getMemoryUsed());

			}

			// null : not error
			if (solution.getResult() == null) {
				solution.setResult("Accepted");
			}
			solution.setRuntime(timeUsed);
			solution.setMemory(memoryUsed);
			solution.setTestResults(tests);
		}
	}

	public static void main(String[] args) {
		Solution solution = new Solution();
		User user = new User();
		user.setUserId(1);
		solution.setUserId(user.getUserId());
		solution.setProblemId(1);
		String code = "#include <stdio.h>\r\n" + "\r\n" + "int main()\r\n" + "{\r\n" + "int a, b;\r\n"
				+ "scanf(\"%d%d\", &a, &b);\r\n" + "printf(\"%d\\n\", a+b);\r\n" + "return 0;\r\n" + "}";
//        String cmd = "python test.py a+b.c testdata 3";
//		String cmd = "python /home/nicolas/Lo-runner-master/demo/test.py /home/nicolas/Lo-runner-master/demo/a+b.c /home/nicolas/Lo-runner-master/demo/testdata 3";

		// nicolas@ubuntu:~/Lo-runner-master/demo$ python test.py a+b.c testdata 3
//        {'memoryused': 6572L, 'timeused': 0L, 'result': 'Accepted'}
//        {'memoryused': 6640L, 'timeused': 0L, 'result': 'Accepted'}
//        {'memoryused': 6820L, 'timeused': 0L, 'result': 'Accepted'}

		solution.setCode(code);
		new Judge().judge(solution);
	}
}
