package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.data.KG.EntityMapper;
import com.example.demo.data.KG.PropertyMapper;
import com.example.demo.po.EntityPo;
import com.example.demo.po.PropertyPo;

import java.util.ArrayList;

public class TreeInfoVo {
    public ArrayList<JSONObject> nodes;
    public TreeInfoVo(){
        nodes = new ArrayList<>();
    }

    public JSONObject getNode(String id, EntityMapper entityMapper, PropertyMapper propertyMapper ){
        for(JSONObject item:nodes){
            if(item.getString("id").equals(id)) return item;
        }
        return createNode(id,entityMapper,propertyMapper);
    }

    private JSONObject createNode(String id, EntityMapper entityMapper, PropertyMapper propertyMapper){
        JSONObject jo = new JSONObject();
        jo.put("id",id);
        EntityPo e = entityMapper.getByRecordId(id);
        PropertyPo p = propertyMapper.getByRecordId(id);
        jo.put("name",(p==null?e:p).toString());
        jo.put("children",new JSONArray());
        nodes.add(jo);
        return jo;
    }

    public JSONObject addProperty(String parentId,String childId, EntityMapper entityMapper, PropertyMapper propertyMapper){
        JSONObject child = createNode(childId,entityMapper,propertyMapper);
        getNode(parentId,entityMapper,propertyMapper).getJSONArray("children").add(child);
        return child;
    }

    public void propertyAdd(JSONObject parent,String childId, EntityMapper entityMapper, PropertyMapper propertyMapper){
        JSONObject child = getNode(childId,entityMapper,propertyMapper);
        parent.getJSONArray("children").add(child);
    }
}
