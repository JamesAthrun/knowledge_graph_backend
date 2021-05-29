package com.example.demo.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AccountVo {
    public String name;
    public String pwd;
    public String email;

    public AccountVo(String jsonStr) {
        JSONObject jo = JSON.parseObject(jsonStr);
        name = jo.getString("name");
        pwd = jo.getString("pwd");
        email = jo.getString("email");
    }

}
