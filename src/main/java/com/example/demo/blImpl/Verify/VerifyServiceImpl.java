package com.example.demo.blImpl.Verify;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bl.Verify.VerifyService;
import com.example.demo.data.Verify.VerifyMapper;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import com.example.demo.util.Trans;
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
    @Autowired
    GlobalLogger logger;

    @Override
    public ResultBean getDesKey(String ip, String modulus, String exponent) throws Exception {

        RSAPublicKeySpec spec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(exponent));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = factory.generatePublic(spec);

        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        Key desKey = KeyGenerator.getInstance("DES").generateKey();

        String keyStr = Trans.bytesToHexStr(desKey.getEncoded());
        logger.log("for ip " + ip + " gen key " + keyStr);
        verifyMapper.insert(ip, keyStr);

        byte[] t0 = desKey.getEncoded();
        String s = Trans.bytesToHexStr(t0);

        byte[] t1 = rsaCipher.doFinal(s.getBytes());
        JSONObject jo = new JSONObject();
        jo.put("key", Trans.BytesToBase64Str(t1));

        return ResultBean.success(jo);
    }
}