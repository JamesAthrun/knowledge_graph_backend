package com.example.demo.anno;

import java.lang.annotation.*;


@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CrypAnno {
    String someAttr() default "";   //注解的属性，这里只是做个样子，没有具体用到
}
