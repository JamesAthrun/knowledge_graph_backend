package com.example.demo.util;

import com.alibaba.fastjson.JSON;

public class ResultBean {
    public int code;
    public String message;
    public String data;

    public static ResultBean error(int code, String message) {
        ResultBean res = new ResultBean();
        res.code = code;
        res.message = message;
        return res;
    }

    public static ResultBean success(){
        ResultBean res = new ResultBean();
        res.code = 1;
        return res;
    }

    public static ResultBean success(Object data) {
        ResultBean res = new ResultBean();
        res.code = 1;
        res.data = JSON.toJSONString(data);
        return res;
    }
}
