package com.example.demo.anno;

import com.example.demo.data.Verify.VerifyMapper;
import com.example.demo.util.GlobalLogger;
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
public class AuthAnnoLogic {
    @Autowired
    GlobalLogger logger;
    @Autowired
    VerifyMapper verifyMapper;

    @Pointcut(value = "@annotation(com.example.demo.anno.AuthAnno)")
    private void aspectJMethod() {
    } //被注解的方法

    @Around("aspectJMethod()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = AnnoUtil.getArgOfUniqueParamType(HttpServletRequest.class,joinPoint);
        String ip = request.getRemoteAddr();

        String KeyByClient = Trans.secretStrToPlainStr(ip,verifyMapper,AnnoUtil.getCookieValueOfUniqueName("user_key",request));
        String KeyByServer = verifyMapper.getDesKey(ip);

        if(!KeyByClient.equals(KeyByServer)) throw new Exception();

        String UserNameByClient = AnnoUtil.getArgOfUniqueAnno(AuthUserName.class,joinPoint);
        String UserNameByServer = verifyMapper.getUserNameByIp(ip);

        if(!UserNameByClient.equals(UserNameByServer)) throw new Exception();
        return joinPoint.proceed(joinPoint.getArgs());
    }

}