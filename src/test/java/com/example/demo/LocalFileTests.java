package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.GlobalConfigure;
import com.example.demo.util.Trans;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class LocalFileTests {

    @Autowired
    GlobalConfigure gc;

    @Test
    void init() {
        JSONObject jo = JSONObject.parseObject(Trans.getJsonString("src/main/resources/data.json"));
    }
}
