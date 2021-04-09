package com.example.demo.blImpl.Verify;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bl.Verify.VerifyService;
import com.example.demo.data.Verify.VerifyMapper;
import com.example.demo.util.GlobalTrans;
import com.example.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

@Service
public class VerifyServiceImpl implements VerifyService {

    @Autowired
    VerifyMapper verifyMapper;

    @Override
    public ResultBean getDesKey(String ip,String modulus, String exponent) throws Exception {

        RSAPublicKeySpec spec = new RSAPublicKeySpec(new BigInteger(modulus),new BigInteger(exponent));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = factory.generatePublic(spec);

        Cipher rsaCipher= Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        Key desKey = KeyGenerator.getInstance("DES").generateKey();
        verifyMapper.insert(ip, GlobalTrans.BytesToStr(desKey.getEncoded()));

        byte[] t0 = desKey.getEncoded();
        JSONArray ja = new JSONArray();
        for(byte b:t0){
            ja.add(String.valueOf(b));
        }
        byte[] t1 = rsaCipher.doFinal(ja.toJSONString().getBytes());
        JSONObject jo = new JSONObject();
        jo.put("key",GlobalTrans.BytesToStr(t1));

        return ResultBean.success(jo);
    }
}