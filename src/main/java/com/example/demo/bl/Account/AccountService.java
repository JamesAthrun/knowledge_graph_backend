package com.example.demo.bl.Account;

import com.example.demo.util.ResultBean;

public interface AccountService {

    ResultBean login(String name,String pwd);

    ResultBean register(String name, String pwd, String email);
}
