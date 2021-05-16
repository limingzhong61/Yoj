package com.yoj.learn;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LearnTest {

    public static void main(String[] args) {
        Path path = Paths.get("path1","path2");
        System.out.println(path);
    }
}
