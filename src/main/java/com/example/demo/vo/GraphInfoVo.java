package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.po.EntityPo;
import com.example.demo.po.PropertyPo;
import com.example.demo.po.TriplePo;

public class GraphInfoVo {

    public JSONArray entityData = new JSONArray();
    public JSONArray propertyData = new JSONArray();
    public JSONArray link = new JSONArray();

    public void addData(EntityPo entityPo){
        JSONObject item = new JSONObject();
        item.put("id",entityPo.recordId);
        item.put("name",entityPo.id);
        String des = "";
        if(!entityPo.nameCn.equals("")) des += "中文名 "+entityPo.nameCn+"\n";
        if(!entityPo.nameEn.equals("")) des += "英文名 "+entityPo.nameEn+"\n";
        if(!entityPo.comment.equals("")) des += "评论 "+entityPo.comment+"\n";
        item.put("des",des);
        entityData.add(item);
    }

    public void addData(PropertyPo propertyPO){
        JSONObject item = new JSONObject();
        item.put("id", propertyPO.recordId);
        item.put("name", propertyPO.id);
        String des = "";
        if(!propertyPO.nameCn.equals("")) des += "中文名 "+ propertyPO.nameCn+"\n";
        if(!propertyPO.nameEn.equals("")) des += "英文名 "+ propertyPO.nameEn+"\n";
        if(!propertyPO.comment.equals("")) des += "评论 "+propertyPO.comment+"\n";
        if(!propertyPO.domain.equals("")) des += "定义域 "+ propertyPO.domain+"\n";
        if(!propertyPO.range.equals("")) des += "值域 "+ propertyPO.range+"\n";
        item.put("des",des);
        propertyData.add(item);
    }

    public void addLink(TriplePo triplePo){
        JSONObject item = new JSONObject();
        item.put("id",triplePo.recordId);
        item.put("name",triplePo.relation);
        item.put("source",triplePo.head);
        item.put("target",triplePo.tail);
        link.add(item);
    }
}
