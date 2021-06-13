package com.example.demo.anno;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class AnnoUtil {

    protected static <T> Integer getArgIndexOfUniqueParamType(Class<T> t, ProceedingJoinPoint joinPoint){
        int i = 0;
        for(Object o:joinPoint.getArgs()){
            if(o.getClass().equals(t)) {
                return i;
            }
            i += 1;
        }
        return -1;
    }

    protected static <T,K extends Annotation> Integer getArgIndexOfUniqueAnno(Class<K> annotation, ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] parameters = method.getParameters();
        for(int i=0;i<parameters.length;i++){
            Parameter parameter = parameters[i];
            if(parameter.getAnnotation(annotation)!=null){
                return i;
            }
        }
        return -1;
    }


    protected static <T> T getArgOfUniqueParamType(Class<T> t, ProceedingJoinPoint joinPoint){
        for(Object o:joinPoint.getArgs()){
            if(classEqual(o,t) || interfaceEqual(o,t)){
                return (T)o;
            }
        }
        return null;
    }

    private static boolean classEqual(Object o,Class<?> t){
        if(o.getClass().isInterface()) return false;
        else return o.getClass().equals(t);
    }

    private static boolean interfaceEqual(Object o,Class<?> t){
        for(Class<?> inter:o.getClass().getInterfaces()){
            if(inter.equals(t)) return true;
        }
        return false;
    }

    protected static <T,K extends Annotation> T getArgOfUniqueAnno(Class<K> annotation, ProceedingJoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();

        for(int i=0;i<parameters.length;i++){
            Parameter parameter = parameters[i];
            if(parameter.getAnnotation(annotation)!=null){
                Object o = args[i];
                return  (T)o;
            }
        }
        return null;
    }

    protected static String getCookieValueOfUniqueName(String name, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
