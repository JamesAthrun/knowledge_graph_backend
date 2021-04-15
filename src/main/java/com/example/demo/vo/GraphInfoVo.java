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
        link.add(triplePo.toJSONObject());
    }

}
