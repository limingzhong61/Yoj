package com.yoj.nuts.judge.util;

//@Slf4j
public class JudgeUtil {
//    @Autowired
//    @Qualifier("remoteExecutor")
//    @Qualifier("localExecutor")

//    private ExecutorUtil executor;

//    public void parseToResult(String cmd, Solution solution) {
//        ExecMessage exec = executor.execute(cmd);
//        if (exec.getError() != null) {
//            solution.setErrorMessage(exec.getError());
//            solution.setResult(Results.SystemError);
//            log.error("=====error====" + solution.getSolutionId() + exec.getStdout() + "    :" + exec.getError());
//        } else {
//            try {
//                System.out.println("=====stdout====" + exec.getStdout());
//                String jsonFormat = "[" + exec.getStdout() + "]";
//                List<TestResult> outs = JSONArray.parseArray(jsonFormat, TestResult.class);
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

//    String compile(int compilerId, String path);
//
//    void deleteSolutionFile(String linuxPath, String windowsPath);
//
//    void createSolutionFile(Solution solution, String linuxPath, String windowsPath);
}
