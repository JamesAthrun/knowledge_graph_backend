package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.example.demo.data.Verify.VerifyMapper;

import javax.crypto.Cipher;
import java.security.Key;

import static com.example.demo.util.GlobalTrans.bytesToStr;

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

    public static ResultBean secret(Object data, VerifyMapper verifyMapper, String ip) throws Exception {
        ResultBean res = new ResultBean();
        res.code = 1;
        String hexStr = verifyMapper.getDesKey(ip);
        Key key = GlobalTrans.getDesKeyFromHexString(hexStr);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        String js = JSON.toJSONString(data);
        res.data = bytesToStr(cipher.doFinal(js.getBytes()));
        return res;
    }
}
