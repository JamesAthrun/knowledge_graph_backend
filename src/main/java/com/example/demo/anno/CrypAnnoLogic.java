package com.example.demo.anno;

import com.example.demo.data.Verify.VerifyMapper;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import com.example.demo.util.Trans;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class CrypAnnoLogic {
    @Autowired
    GlobalLogger logger;
    @Autowired
    VerifyMapper verifyMapper;

    /**
     * 切点我这儿定义的是为controller包下的所有类，所有方法都加
     * 你可以指定具体的类或具体的方法
     */
    @Pointcut(value = "@annotation(com.example.demo.anno.CrypAnno)")
    private void aspectJMethod() {
    } //被注解的方法

    @Around("aspectJMethod()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        logger.log("aop start");
        Object[] objs = joinPoint.getArgs();
        HttpServletRequest request = ((HttpServletRequest) objs[0]);
        if (request.getMethod().equals("POST")) {
            String ip = request.getRemoteAddr();
            String body = (String) objs[1];
            String plainStr = Trans.secretStrToPlainStr(ip, verifyMapper, body);

            objs[1] = plainStr;
//            objs[1] = getConvertVoClass(joinPoint).getConstructor(String.class).newInstance(plainStr);
            ResultBean res = (ResultBean) joinPoint.proceed(objs);
            logger.log("handle result bean");
            res.setAsSecret(verifyMapper, ip);
            return res;
        } else if (request.getMethod().equals("GET")) {
            //todo 使用该注解后，get请求的参数不加密，但返回的data加密
            return joinPoint.proceed(joinPoint.getArgs());
        } else return joinPoint.proceed(joinPoint.getArgs());
    }

    private Class<?> getConvertVoClass(JoinPoint joinPoint) throws Exception {
        String mName = joinPoint.getSignature().getName();
        Method[] ms = joinPoint.getSignature().getDeclaringType().getDeclaredMethods();
        Method joinMethod = null;
        for (Method m :
                ms) {
            if (m.getName().equals(mName)) {
                joinMethod = m;
                break;
            }
        }
        if (joinMethod == null) throw new Exception();
        return joinMethod.getParameterTypes()[1];
    }
}