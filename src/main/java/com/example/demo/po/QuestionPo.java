package com.example.demo.po;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionPo {
    public String keyWords;
    public String help;
    public String relatedIds;

    public List<String> getKeyWords(){
        return JSONArrayToList(keyWords);
    }

    public List<String> getRelatedIds(){
        return JSONArrayToList(relatedIds);
    }

    private List<String> JSONArrayToList(String s){
        JSONArray ja = JSON.parseArray(s);
        List<String> res = new ArrayList<>();
        int count = 0;
        for (Object item: ja){
            JSONObject jo = (JSONObject)(item);
            res.add(jo.getString(String.valueOf(count++)));
        }
        return res;
    }
}
