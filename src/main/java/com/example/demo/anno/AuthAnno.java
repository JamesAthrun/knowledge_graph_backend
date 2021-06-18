package com.example.demo.anno;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//使用处的方法必须带有类型为HttpServletRequest的参数
public @interface AuthAnno {
    String someAttr() default "";   //注解的属性，这里只是做个样子，没有具体用到

    String level() default "";   //在这里写入需要的权限，如"r","w",""，""代表不需要权限
}
