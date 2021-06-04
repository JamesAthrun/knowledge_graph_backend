package com.example.demo.blImpl.AccountServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bl.Account.AccountService;
import com.example.demo.data.Account.AccountMapper;
import com.example.demo.data.Account.UserGroupMapper;
import com.example.demo.data.Verify.VerifyMapper;
import com.example.demo.po.AccountPo;
import com.example.demo.po.GroupPo;
import com.example.demo.po.UserGroupPo;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountMapper accountMapper;
    @Autowired
    UserGroupMapper userGroupMapper;
    @Autowired
    VerifyMapper verifyMapper;
    @Autowired
    GlobalLogger logger;

    @Override
    public ResultBean login(String name, String pwd) {
        AccountPo accountPo = accountMapper.selectAccountByName(name);
        if (pwd.equals(accountPo.pwd)) {
            JSONObject jo = new JSONObject();
            return ResultBean.success();
        } else {
            logger.log("pwd not match");
            return ResultBean.error(3, "pwd not match");
        }
    }

    @Override
    public ResultBean register(String name, String pwd, String email) {
        accountMapper.register(new AccountPo(name, pwd, email));
        return ResultBean.success();
    }

    @Override
    public ResultBean getGroupList(int userId) {
        return ResultBean.success(userGroupMapper.selectGroupsByUserId(userId));
    }
}
