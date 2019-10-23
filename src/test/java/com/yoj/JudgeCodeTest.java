package com.yoj;

import com.yoj.nuts.judge.Judge;
import com.yoj.nuts.judge.bean.static_fianl.Languages;
import com.yoj.nuts.judge.bean.static_fianl.Results;
import com.yoj.web.bean.Solution;
import com.yoj.web.service.ProblemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JudgeCodeTest {

    @Autowired
    ProblemService problemService;

    @Autowired
    Judge judge;

    @Test
    public void judgeAll() throws Exception{
        testJudgeC();
        testJudgeCPP();
        testJudgeJava();
        testJudgePython();
    }

    @Test
    public void testJudgeC()  throws Exception{
        Solution solution = new Solution();
        solution.setUserId(1);
        solution.setLanguage(Languages.C);
        solution.setCode("#include <stdio.h>\n" +
                " \n" +
                "int main()\n" +
                "{\n" +
                "    int a, b;\n" +
                "    scanf(\"%d%d\", &a, &b);\n" +
                "    printf(\"%d\", a+b);\n" +
                "    return 0;\n" +
                "}");
        judge.judge(solution, problemService.queryById(1));
        if(solution.getResult() != Results.Accepted){
            throw new Exception("judge c error");
        }
    }

    @Test
    public void testJudgeCPP()  throws Exception{
        Solution solution = new Solution();
        solution.setUserId(1);
        solution.setLanguage(Languages.CPP);
        solution.setCode("#include <iostream>\n" +
                " \n" +
                "using namespace std;\n" +
                " \n" +
                "int main()\n" +
                "{\n" +
                "    int a, b;\n" +
                "    cin >> a >> b;\n" +
                "    cout << a + b;\n" +
                "    return 0;\n" +
                "}");
        judge.judge(solution, problemService.queryById(1));
        if(solution.getResult() != Results.Accepted){
            throw new Exception("judge c++ error");
        }
    }

    @Test
    public void testJudgeJava()  throws Exception{
        Solution solution = new Solution();
        solution.setUserId(1);
        solution.setLanguage(Languages.JAVA);
        solution.setCode("import java.util.*;\n" +
                " \n" +
                "public class Main\n" +
                "{\n" +
                "    public static void main(String args[])\n" +
                "    {\n" +
                "        Scanner sc = new Scanner(System.in);\n" +
                "        Integer a = sc.nextInt();\n" +
                "        Integer b = sc.nextInt();\n" +
                "        System.out.print(a + b);\n" +
                "    }\n" +
                "}");
        judge.judge(solution, problemService.queryById(1));
        if(solution.getResult() != Results.Accepted){
            throw new Exception("judge java error");
        }
    }



    @Test
    public void testJudgePython() throws Exception{
        Solution solution = new Solution();
        solution.setUserId(1);
        solution.setLanguage(Languages.PYTHON);
        solution.setCode("print(sum(map(int, input().split())), end='')");
        judge.judge(solution, problemService.queryById(1));
        if(solution.getResult() != Results.Accepted){
            throw new Exception("judge python error");
        }
    }


}
