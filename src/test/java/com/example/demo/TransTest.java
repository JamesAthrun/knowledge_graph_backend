package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.GlobalTrans;
import com.example.demo.vo.KGEditFormVo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransTest {
    @Test
    void formTest1(){
        KGEditFormVo f = new KGEditFormVo("1","1","1","1","1","1","1","1","1","1","1");
        assertEquals(
                GlobalTrans.javaObjectToJsonStr(f),
                GlobalTrans.javaObjectToJsonStr(
                        GlobalTrans.jsonStrToJavaObject(
                                (GlobalTrans.javaObjectToJsonStr(f)),f.getClass()))
        );
    }

    @Test
    void formTest2(){
        KGEditFormVo f = new KGEditFormVo("1","1","1","1","1","1","1","1","1","1","1");
        assertEquals(
                GlobalTrans.javaObjectToJSONObject(f),
                JSONObject.parse(GlobalTrans.javaObjectToJsonStr(f))
        );
    }

    @Test
    void tmp(){
        System.out.println(JSON.toJSONString("aaa"));
    }
}
