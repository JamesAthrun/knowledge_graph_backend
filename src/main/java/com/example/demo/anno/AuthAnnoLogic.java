package com.example.demo.anno;

import com.example.demo.bl.KG.KGService;
import com.example.demo.data.Account.AccountMapper;
import com.example.demo.data.KG.GraphMapper;
import com.example.demo.data.Verify.VerifyMapper;
import com.example.demo.po.AccountPo;
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
    @Autowired
    GraphMapper graphMapper;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    KGService kgService;

    @Pointcut(value = "@annotation(com.example.demo.anno.AuthAnno)")
    private void aspectJMethod() {
    } //被注解的方法

    @Around("aspectJMethod() && @annotation(authAnno)")
    public Object doAround(ProceedingJoinPoint joinPoint,AuthAnno authAnno) throws Throwable {
        HttpServletRequest request = AnnoUtil.getArgOfUniqueParamType(HttpServletRequest.class,joinPoint);
        if(request==null) throw new Exception();
        String ip = request.getRemoteAddr();

        String KeyByClient = Trans.secretStrToPlainStr(ip,verifyMapper,AnnoUtil.getCookieValueOfUniqueName("user_key",request));
        String KeyByServer = verifyMapper.getDesKey(ip);
        if(!KeyByClient.equals(KeyByServer)) throw new Exception();

        //根据用户ip查到的userName，且持有正确的key，可以认为是可靠的
        String UserNameByServer = verifyMapper.getUserNameByIp(ip);
        String TableIdByClient = AnnoUtil.getCookieValueOfUniqueName("table_id",request);
        //todo 根据authAnno的属性level来判断发起该请求的用户是否拥有操作tableId对应图谱的权限
        String levelToOp = authAnno.level();//"r" "w" ""
        if(!levelToOp.equals("")){
            String authStr = graphMapper.selectAuthority(TableIdByClient);
            if(levelToOp.equals("r")) {
                AccountPo accountPo = accountMapper.selectAccountByName(UserNameByServer);
                boolean permission = kgService.getReadPermission(TableIdByClient, accountPo.userId);
                if (!permission) throw new Exception();
            }
            if(levelToOp.equals("w")) {
                AccountPo accountPo = accountMapper.selectAccountByName(UserNameByServer);
                boolean permission = kgService.getWritePermission(TableIdByClient, accountPo.userId);
                if (!permission) throw new Exception();
            }
        }


        //若带有AuthUserNameAnno的注解，则赋为真实的userName
        int ArgUserNameIndex = AnnoUtil.getArgIndexOfUniqueAnno(AuthUserNameAnno.class,joinPoint);
        if(ArgUserNameIndex!=-1) joinPoint.getArgs()[ArgUserNameIndex] = UserNameByServer;

        //若带有AuthUserNameAnno的注解，则赋为tableId
        int ArgTableIdIndex = AnnoUtil.getArgIndexOfUniqueAnno(AuthTableIdAnno.class,joinPoint);
        if(ArgTableIdIndex!=-1) joinPoint.getArgs()[ArgTableIdIndex] = TableIdByClient;

        return joinPoint.proceed(joinPoint.getArgs());
    }

}