package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.data.KG.ItemMapper;
import com.example.demo.po.ItemPo;

public class AnswerVo {
    public JSONArray table;
    public String help;
    private int count;
    private final ItemMapper itemMapper;

    public AnswerVo(ItemMapper e, String h) {
        table = new JSONArray();
        itemMapper = e;
        help = h;
    }

    private void addEntity(ItemPo e) {
        JSONObject jo = new JSONObject();
        if (!e.division.equals("String")) return;
        String context = e.id.substring(1);
        jo.put(String.valueOf(count++), context);
        table.add(jo);
    }

    public void addTableItem(String id) {
        ItemPo e = itemMapper.getById(id);
        addEntity(e);
    }

}
