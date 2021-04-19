package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.data.KG.EntityMapper;
import com.example.demo.data.KG.PropertyMapper;
import com.example.demo.po.EntityPo;
import com.example.demo.po.PropertyPo;
import com.example.demo.po.TriplePo;

public class GraphInfoVo {
    private final EntityMapper entityMapper;
    private final PropertyMapper propertyMapper;
    public JSONArray entityData;
    public JSONArray propertyData;
    public JSONArray link;

    public GraphInfoVo(EntityMapper e, PropertyMapper p) {
        entityMapper = e;
        propertyMapper = p;
        entityData = new JSONArray();
        propertyData = new JSONArray();
        link = new JSONArray();
    }

    private void addData(EntityPo entityPo) {
        if (entityData.contains(entityPo.toJSONObject())) return;
        entityData.add(entityPo.toJSONObject());
    }

    private void addData(PropertyPo propertyPO) {
        if (propertyData.contains(propertyPO.toJSONObject())) return;
        propertyData.add(propertyPO.toJSONObject());
    }

    public void addLink(TriplePo triplePo) {
        String[] ids = {triplePo.head, triplePo.relation, triplePo.tail};
        for (String id : ids) {
            EntityPo e = entityMapper.getByRecordId(id);
            PropertyPo p = propertyMapper.getByRecordId(id);
            if (e != null) this.addData(e);
            if (p != null) this.addData(p);
        }
        link.add(triplePo.toJSONObject());
    }
}
