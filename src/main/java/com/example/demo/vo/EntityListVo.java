package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class EntityListVo {

    public JSONArray data = new JSONArray();

    public void addEntity(String id, String name){
        JSONObject item = new JSONObject();
        item.put("id",id);
        item.put("name",name);
        data.add(item);
    }

}
