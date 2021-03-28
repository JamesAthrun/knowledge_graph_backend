package com.example.demo.bl.Account;

import com.example.demo.util.ResultBean;

public interface AccountService {

    ResultBean register(String name,String pwd);
    ResultBean login(String name,String pwd);
}
