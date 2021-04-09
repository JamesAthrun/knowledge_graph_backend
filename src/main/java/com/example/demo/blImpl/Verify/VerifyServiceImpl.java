package com.example.demo.blImpl.Verify;

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
        System.out.println("ip from "+ip+" gen key");
        RSAPublicKeySpec spec = new RSAPublicKeySpec(new BigInteger(modulus),new BigInteger(exponent));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = factory.generatePublic(spec);

        Cipher rsaCipher= Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        Key desKey = KeyGenerator.getInstance("DES").generateKey();
        verifyMapper.insert(ip, GlobalTrans.bytesToHexStr(desKey.getEncoded()));

        byte[] t0 = desKey.getEncoded();
        String s = GlobalTrans.bytesToHexStr(t0);

        byte[] t1 = rsaCipher.doFinal(s.getBytes());
        JSONObject jo = new JSONObject();
        jo.put("key",GlobalTrans.BytesToBase64Str(t1));

        return ResultBean.success(jo);
    }
}