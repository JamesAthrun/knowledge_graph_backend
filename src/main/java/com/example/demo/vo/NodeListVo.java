package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.po.EntityPo;
import com.example.demo.po.PropertyPo;

public class NodeListVo {

    public JSONArray data = new JSONArray();

    public void addEntity(EntityPo entityPo){
        data.add(entityPo.toJSONObject());
    }

    public void addProperty(PropertyPo propertyPO){
        data.add(propertyPO.toJSONObject());
    }

}
