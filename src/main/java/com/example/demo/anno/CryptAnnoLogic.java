package com.example.demo.anno;

import com.example.demo.data.Verify.VerifyMapper;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import com.example.demo.util.Trans;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class CryptAnnoLogic {
    @Autowired
    GlobalLogger logger;
    @Autowired
    VerifyMapper verifyMapper;

    @Pointcut(value = "@annotation(com.example.demo.anno.CryptAnno)")
    private void aspectJMethod() {
    } //被注解的方法

    @Around("aspectJMethod()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] objs = joinPoint.getArgs();
        HttpServletRequest request = AnnoUtil.getArgOfUniqueParamType(HttpServletRequest.class,joinPoint);

        if (request.getMethod().equals("POST")) {
            String ip = request.getRemoteAddr();
            String body = AnnoUtil.getArgOfUniqueParamType(String.class,joinPoint);
            String plainStr = Trans.secretStrToPlainStr(ip, verifyMapper, body);
            int index = AnnoUtil.getArgIndexOfUniqueParamType(String.class,joinPoint);
            objs[index] = plainStr;
            ResultBean res = (ResultBean) joinPoint.proceed(objs);
            res.setAsSecret(verifyMapper, ip);
            return res;
        } else if (request.getMethod().equals("GET")) {
            //todo 使用该注解后，get请求的参数不加密，但返回的data加密
            return joinPoint.proceed(joinPoint.getArgs());
        } else throw new Exception();
    }


}