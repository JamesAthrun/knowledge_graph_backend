package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.data.KG.EntityMapper;
import com.example.demo.data.KG.PropertyMapper;
import com.example.demo.po.EntityPo;
import com.example.demo.po.PropertyPo;

import java.util.ArrayList;

public class TreeInfoVo {
    private final String root;
    private final EntityMapper entityMapper;
    private final PropertyMapper propertyMapper;
    public ArrayList<JSONObject> nodes;

    public TreeInfoVo(String r,EntityMapper e,PropertyMapper p){
        this.root = r;
        this.entityMapper = e;
        this.propertyMapper = p;
        nodes = new ArrayList<>();
    }

    public JSONObject getNode(String id){
        for(JSONObject item:nodes){
            if(item.getString("id").equals(id)) return item;
        }
        return createNode(id);
    }

    private JSONObject createNode(String id){
        JSONObject jo = new JSONObject();
        jo.put("id",id);
        EntityPo e = entityMapper.getByRecordId(id);
        PropertyPo p = propertyMapper.getByRecordId(id);
        jo.put("name",(p==null?e:p).toString());
        jo.put("children",new JSONArray());
        nodes.add(jo);
        return jo;
    }

    public JSONObject addProperty(String parentId,String childId){
        JSONObject child = createNode(childId);
        getNode(parentId).getJSONArray("children").add(child);
        return child;
    }

    public void propertyAdd(JSONObject parent,String childId){
        JSONObject child = createNode(childId);
        parent.getJSONArray("children").add(child);
    }

    public JSONObject getRoot(){
        return getNode(root);
    }

}
