package com.yoj.judge.bean.static_fianl;

public class Languages {
    public static final Integer C = 0;
    public static final Integer CPP = 1;
    public static final Integer JAVA = 2;
    public static final Integer PYTHON = 3;
    private static final String[] NAMES= {"C","C++","Java","Python"};

    public static String toString(Integer language) {
        return NAMES[language];
    }
}

