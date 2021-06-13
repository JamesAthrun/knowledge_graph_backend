package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.Trans;
import com.example.demo.vo.KGEditFormVo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransTest {
    @Test
    void formTest1() {
        KGEditFormVo f = new KGEditFormVo("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        assertEquals(
                Trans.javaObjectToJsonStr(f),
                Trans.javaObjectToJsonStr(
                        Trans.jsonStrToJavaObject(
                                (Trans.javaObjectToJsonStr(f)), f.getClass()))
        );
    }

    @Test
    void formTest2() {
        KGEditFormVo f = new KGEditFormVo("1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1");
        assertEquals(
                Trans.javaObjectToJSONObject(f),
                JSONObject.parse(Trans.javaObjectToJsonStr(f))
        );
    }

    @Test
    void tmp() {
        System.out.println(JSON.toJSONString("aaa"));
    }
}
