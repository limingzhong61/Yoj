package com.yoj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YojApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void createCFile() {
        // String path = request.getSession().getServletContext().getRealPath("/");
//        System.out.println(path);

        //创建文件
        String path = new File("").getAbsolutePath();
        String fileName = path + "\\a+b" + ".c";
        File file = new File(fileName);
        String code = "#include <stdio.h>\n" +
                "\n" +
                "int main()\n" +
                "{\n" +
                "    int a, b;\n" +
                "    scanf(\"%d%d\", &a, &b);\n" +
                "    printf(\"%d\\n\", a + b);\n" +
                "}";

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //写入文件
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            printWriter.write(code.toCharArray());
            printWriter.flush();
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//      删除文件
//        file.delete();
        System.out.println(fileName);

    }
}
