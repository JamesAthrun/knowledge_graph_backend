package com.example.demo.blImpl.AccountServiceImpl;

import com.example.demo.bl.Account.AccountService;
import com.example.demo.data.Account.AccountMapper;
import com.example.demo.data.Verify.VerifyMapper;
import com.example.demo.po.AccountPo;
import com.example.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    VerifyMapper verifyMapper;

    @Override
    public ResultBean login(String name, String pwd) {
        String pwd_true = accountMapper.selectPwdByName(name);
        if(pwd.equals(pwd_true))
            return ResultBean.success();
        else{
            System.out.println("pwd not match");
            return ResultBean.error(3,"pwd not match");
        }
    }

    @Override
    public ResultBean register(String name,String pwd, String email){
        accountMapper.register(new AccountPo(name,pwd,email));
        return ResultBean.success();
    }
}
