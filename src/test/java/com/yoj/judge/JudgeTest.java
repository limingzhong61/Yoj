package com.yoj.judge;

import com.yoj.judge.bean.static_fianl.Languages;
import com.yoj.judge.bean.static_fianl.Results;
import com.yoj.web.bean.Solution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JudgeTest {
    @Autowired
    Judge judge;

    @Test
    public void testJudgeC(){
        String c = "#include <stdio.h>\n" +
                "\n" +
                "int main()\n" +
                "{\n" +
                "    int a, b;\n" +
                "    scanf(\"%d%d\", &a, &b);\n" +
                "    printf(\"%d\\n\", a+b);\n" +
                "    return 0;\n" +
                "}";
        Solution solution = new Solution();
        solution.setLanguage(Languages.C);
        solution.setCode(c);
        judge.judge(solution);
        if(solution.getResult() != Results.Accepted){
            int i = 1 / 0;
        }
    }

    @Test
    public void testJudgeCpp(){
        String cpp = "#include <iostream>\n" +
                "\n" +
                "using namespace std;\n" +
                "\n" +
                "int main()\n" +
                "{\n" +
                "    int a, b;\n" +
                "    cin >> a >> b;\n" +
                "    cout << a + b << endl;\n" +
                "    return 0;\n" +
                "}";
        Solution solution = new Solution();
        solution.setLanguage(Languages.CPP);
        solution.setCode(cpp);
        judge.judge(solution);
        if(solution.getResult() != Results.Accepted){
            int i = 1 / 0;
        }
    }

    @Test
    public void testJudgeJava(){
        String java = "import java.util.*;\n" +
                "\n" +
                "public class Main\n" +
                "{\n" +
                "    public static void main(String args[])\n" +
                "    {\n" +
                "        Scanner sc = new Scanner(System.in);\n" +
                "        Integer a = sc.nextInt();\n" +
                "        Integer b = sc.nextInt();\n" +
//                "\t\tSystem.out.println(\"out\");\n" +
//                "\t\tSystem.out.println(a);\n" +
//                "\t\tSystem.out.println(a);\n" +
                "        System.out.println(a + b);\n" +
                "    }\n" +
                "}";
        Solution solution = new Solution();
        solution.setLanguage(Languages.JAVA);
        solution.setCode(java);
        judge.judge(solution);
        if(solution.getResult() != Results.Accepted){
            int i = 1 / 0;
        }
    }
}
