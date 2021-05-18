package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.data.KG.ItemMapper;
import com.example.demo.po.ItemPo;
import com.example.demo.po.TriplePo;

public class GraphInfoVo {
    private final ItemMapper itemMapper;
    public JSONArray itemData;
    public JSONArray link;

    public GraphInfoVo(ItemMapper i) {
        itemMapper = i;
        itemData = new JSONArray();
        link = new JSONArray();
    }

    private void addItem(ItemPo itemPo) {
        if (itemData.contains(itemPo.toJSONObject())) return;
        itemData.add(itemPo.toJSONObject());
    }

    public void addLink(TriplePo triplePo) {
        ItemPo h,r,t;
        h = itemMapper.getById(triplePo.head);
        r = itemMapper.getById(triplePo.relation);
        t = itemMapper.getById(triplePo.tail);
        this.addItem(h);
        this.addItem(t);
        link.add(triplePo.toJSONObject(r.name));
    }
}
