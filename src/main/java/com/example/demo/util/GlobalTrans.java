package com.example.demo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GlobalTrans {

    public static String getJsonString(String filepath){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath)), StandardCharsets.UTF_8));
            String line;
            StringBuilder res = new StringBuilder();
            while((line = br.readLine()) != null){
                res.append(line);
            }
            return res.toString();
        }catch (Exception e){
            return null;
        }
    }

    public static String BytesToStr(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] StrToBytes(String s){
        return Base64.getDecoder().decode(s);
    }

}
