package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.po.EntityPo;
import com.example.demo.po.PropertyPO;

public class NodeListVo {

    public JSONArray data = new JSONArray();

    public void addEntity(EntityPo entityPo){
        JSONObject item = new JSONObject();
        item.put("id",entityPo.recordId);
        item.put("name",entityPo.id);
        String des = "";
        if(!entityPo.nameCn.equals("")) des += "中文名 "+entityPo.nameCn+"\n";
        if(!entityPo.nameEn.equals("")) des += "英文名 "+entityPo.nameEn+"\n";
        if(!entityPo.comment.equals("")) des += "评论 "+entityPo.comment+"\n";
        item.put("des",des);
        data.add(item);
    }

    public void addProperty(PropertyPO propertyPO){
        JSONObject item = new JSONObject();
        item.put("id", propertyPO.recordId);
        item.put("name", propertyPO.id);
        String des = "";
        if(!propertyPO.nameCn.equals("")) des += "中文名 "+ propertyPO.nameCn+"\n";
        if(!propertyPO.nameEn.equals("")) des += "英文名 "+ propertyPO.nameEn+"\n";
        if(!propertyPO.comment.equals("")) des += "评论 "+propertyPO.comment+"\n";
        item.put("des",des);
        data.add(item);
    }

}
