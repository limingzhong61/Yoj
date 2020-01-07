package com.yoj.custom.judge.enums;

public enum  Language {
    C("C"),
    CPP("C++"),
    JAVA("Java"),
    PYTHON("Python");

    private  String name;
    Language(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

