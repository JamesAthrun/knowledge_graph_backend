package com.example.demo.po;

public class AccountPo {
    public String name;
    public String pwd;
    public String email;
    public String authority;

    public AccountPo(String name, String pwd, String email, String authority) {
        this.name = name;
        this.pwd = pwd;
        this.email = email;
        this.authority = authority;
    }
}
