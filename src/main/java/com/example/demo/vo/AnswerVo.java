package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.data.KG.ItemMapper;
import com.example.demo.po.ItemPo;
import com.example.demo.po.QuestionPo;

import java.util.List;

public class AnswerVo {
    private final ItemMapper itemMapper;
    private final String ver;
    public JSONArray table;
    public String help;
    private int count;

    public AnswerVo(ItemMapper e, String h, String ver) {
        table = new JSONArray();
        itemMapper = e;
        help = h;
        this.ver = ver;
    }

    public AnswerVo(ItemMapper e, QuestionPo question) {
        this.table = new JSONArray();
        this.itemMapper = e;
        this.help = question.help;
        this.ver = question.ver;
        List<String> relatedIds = question.getRelatedIds();
        for (String id : relatedIds) {
            this.addEntity(itemMapper.getById(id, ver));
        }
    }

    private void addEntity(ItemPo e) {
        JSONObject jo = new JSONObject();
        if (!e.division.equals("String")) return;
        String context = e.title.substring(1);
        jo.put(String.valueOf(count++), context);
        table.add(jo);
    }

    public void addTableItem(String id) {
        ItemPo e = itemMapper.getById(id, ver);
        addEntity(e);
    }
}
