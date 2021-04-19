package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.data.KG.EntityMapper;
import com.example.demo.po.EntityPo;

public class AnswerVo {
    public JSONArray table;
    public String help;
    private int count;
    private final EntityMapper entityMapper;

    public AnswerVo(EntityMapper e, String h) {
        table = new JSONArray();
        entityMapper = e;
        help = h;
    }

    private void addEntity(EntityPo e) {
        JSONObject jo = new JSONObject();
        if (!e.division.equals("String")) return;
        String context = e.id.substring(1);
        jo.put(String.valueOf(count++), context);
        table.add(jo);
    }

    public void addTableItem(String id) {
        EntityPo e = entityMapper.getByRecordId(id);
        addEntity(e);
    }

}
