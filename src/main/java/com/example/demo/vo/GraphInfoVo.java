package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.po.EntityPo;
import com.example.demo.po.PropertyPo;
import com.example.demo.po.TriplePo;

public class GraphInfoVo {

    public JSONArray entityData = new JSONArray();
    public JSONArray propertyData = new JSONArray();
    public JSONArray link = new JSONArray();

    public void addData(EntityPo entityPo){
        entityData.add(entityPo.toJSONObject());
    }

    public void addData(PropertyPo propertyPO){
        propertyData.add(propertyPO.toJSONObject());
    }

    public void addLink(TriplePo triplePo){
//        String name = "";
//        for(Object item: propertyData){
//            JSONObject tmp = (JSONObject)(item);
//            if(tmp.getString("id").equals(triplePo.relation)) name = tmp.getString("name");
//        }
        link.add(triplePo.toJSONObject());

    }
}
