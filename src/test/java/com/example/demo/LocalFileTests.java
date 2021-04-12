package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.GlobalConfigure;
import com.example.demo.util.GlobalTrans;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LocalFileTests {

    @Autowired
    GlobalConfigure gc;

    @Test
    void init() {
        JSONObject jo = JSONObject.parseObject(GlobalTrans.getJsonString(gc.data_path));
        assertTrue(jo.containsKey("entity"));
        assertTrue(jo.containsKey("property"));
        assertTrue(jo.containsKey("triple"));
    }
}
