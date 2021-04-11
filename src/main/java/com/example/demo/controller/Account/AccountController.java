package com.example.demo.controller.Account;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bl.Account.AccountService;
import com.example.demo.data.Verify.VerifyMapper;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.GlobalTrans;
import com.example.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    VerifyMapper verifyMapper;
    @Autowired
    GlobalLogger logger;

    @PostMapping("/login")
    public ResultBean login(HttpServletRequest request, @RequestBody String s) throws Exception {
        logger.log("AccountController login");
        String ip = request.getRemoteAddr();
        JSONObject jo = GlobalTrans.secretJsonStrToJsonObject(ip,verifyMapper,s);
        String name = jo.getString("username"),pwd = jo.getString("password");
        return accountService.login(name,pwd);
    }

    @PostMapping("/signup")
    public ResultBean register(HttpServletRequest request, @RequestBody String s) throws Exception {
        logger.log("AccountController signup");
        String ip = request.getRemoteAddr();
        JSONObject jo = GlobalTrans.secretJsonStrToJsonObject(ip,verifyMapper,s);
        String name = jo.getString("username"),pwd = jo.getString("password"),email = jo.getString("email");
        return accountService.register(name,pwd,email);
    }
}
