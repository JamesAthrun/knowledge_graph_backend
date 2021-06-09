package com.example.demo.anno;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//使用处的方法必须带有类型为HttpServletRequest的参数
//带有该注解的参数会被赋予该请求对应的真实用户名
public @interface AuthTableIdAnno {
    String someAttr() default "";   //注解的属性，这里只是做个样子，没有具体用到
}
