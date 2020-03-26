package com.yoj.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //方法
@Documented
public @interface JudgePermitAnnotation {

}
