package com.example.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.GlobalConfigure;
import com.example.demo.util.Trans;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class LocalFileTests {

    @Autowired
    GlobalConfigure gc;

    @Test
    void graphCreateFile() {
        JSONObject jo = JSONObject.parseObject(Trans.getJsonStringFromPath("src/main/resources/data.json"));
    }

    @Test
    void questionCreateFile() {
        JSONArray ja = JSONArray.parseArray(Trans.getJsonStringFromPath("src/main/resources/question.json"));
    }
}
