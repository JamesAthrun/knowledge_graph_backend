package com.example.demo.blImpl.AccountServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bl.Account.AccountService;
import com.example.demo.data.Account.AccountMapper;
import com.example.demo.data.Verify.VerifyMapper;
import com.example.demo.po.AccountPo;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    VerifyMapper verifyMapper;
    @Autowired
    GlobalLogger logger;

    @Override
    public ResultBean login(String name, String pwd) {
        AccountPo accountPo = accountMapper.selectPwdByName(name);
        if (pwd.equals(accountPo.pwd)) {
            JSONObject jo = new JSONObject();
            jo.put("authority", accountPo.authority);
            return ResultBean.success(jo);
        } else {
            logger.log("pwd not match");
            return ResultBean.error(3, "pwd not match");
        }
    }

    @Override
    public ResultBean register(String name, String pwd, String email) {
        accountMapper.register(new AccountPo(name, pwd, email, "client"));
        return ResultBean.success();
    }
}
