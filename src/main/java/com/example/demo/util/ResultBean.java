package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.example.demo.data.Verify.VerifyMapper;

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

    public static ResultBean success() {
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

    public void setAsSecret(VerifyMapper verifyMapper, String ip) throws Exception {
        if (data == null) return;
        data = Trans.plainStrToSecretStr(ip, verifyMapper, data);
    }

}
