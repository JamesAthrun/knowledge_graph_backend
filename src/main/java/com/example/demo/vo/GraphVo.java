package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class GraphVo {

    public JSONArray data = new JSONArray();
    public JSONArray link = new JSONArray();

    public void addData(String id, String name){
        JSONObject item = new JSONObject();
        item.put("id",id);
        item.put("name",name);
        data.add(item);
    }
    public void addLink(String source, String target, String name){
        JSONObject item = new JSONObject();
        item.put("source",source);
        item.put("target",target);
        item.put("name",name);
        link.add(item);
    }
}
