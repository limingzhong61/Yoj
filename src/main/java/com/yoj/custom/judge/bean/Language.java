package com.yoj.custom.judge.bean;

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

