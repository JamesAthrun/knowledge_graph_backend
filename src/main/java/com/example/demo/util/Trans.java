package com.example.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.data.Verify.VerifyMapper;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class Trans {

    public static String getJsonStringFromPath(String filepath) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath)), StandardCharsets.UTF_8));
            String line;
            StringBuilder res = new StringBuilder();
            while ((line = br.readLine()) != null) {
                res.append(line);
            }
            return res.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String BytesToBase64Str(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] Base64StrToBytes(String s) {
        return Base64.getDecoder().decode(s);
    }

    public static String byteToHexStr(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }

    public static String bytesToHexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(aByte & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] hexStrToBytes(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexStrToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    private static byte hexStrToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    private static Key getDesKeyFromHexString(String hexStr) throws Exception {
        byte[] bytes = hexStrToBytes(hexStr);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        DESKeySpec keySpec = new DESKeySpec(bytes);
        return keyFactory.generateSecret(keySpec);
    }

    public static String bytesToStr(byte[] bs) {
        StringBuilder sb = new StringBuilder();
        for (byte item : bs)
            sb.append((char) item);
        return sb.toString();
    }

    public static String secretStrToPlainStr(String ip, VerifyMapper verifyMapper, String secretJsonStr) throws Exception {
        String hexStr = verifyMapper.getDesKey(ip);
        Key key = Trans.getDesKeyFromHexString(hexStr);

        Cipher cipher = Cipher.getInstance("DES/ECB/ISO10126Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        return bytesToStr(cipher.doFinal(hexStrToBytes(secretJsonStr)));
    }

    public static String plainStrToSecretStr(String ip, VerifyMapper verifyMapper, String plainStr) throws Exception {
        String hexStr = verifyMapper.getDesKey(ip);
        Key key = Trans.getDesKeyFromHexString(hexStr);
        Cipher cipher = Cipher.getInstance("DES/ECB/ISO10126Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return bytesToHexStr(cipher.doFinal(plainStr.getBytes()));
    }

    // JsonStr->JSONObject->JavaObject
    public static <T> T jsonStrToJavaObject(String jsonStr, Class<T> type) {
        return JSON.toJavaObject(JSON.parseObject(jsonStr), type);
    }

    // JavaObject->JSONObject->JsonStr
    public static <T> String javaObjectToJsonStr(T pojo) {
        JSONObject jo = (JSONObject) (JSON.toJSON(pojo));
        return jo.toJSONString();
    }

    // JavaObject->JSONObject
    public static <T> JSONObject javaObjectToJSONObject(T pojo) {
        return (JSONObject) (JSON.toJSON(pojo));
    }

}
