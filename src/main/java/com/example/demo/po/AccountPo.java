package com.example.demo.po;

public class AccountPo {
    public int userId;
    public String name;
    public String pwd;
    public String email;

    public AccountPo(String name, String pwd, String email) {
//        this.userId = userId;
        this.name = name;
        this.pwd = pwd;
        this.email = email;
    }
}
