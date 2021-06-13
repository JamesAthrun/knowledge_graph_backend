package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.data.KG.ItemMapper;
import com.example.demo.po.ItemPo;
import com.example.demo.po.TriplePo;

public class GraphInfoVo {
    private final ItemMapper itemMapper;
    private final String ver;
    public JSONArray itemData;
    public JSONArray link;

    public GraphInfoVo(ItemMapper i, String ver) {
        itemMapper = i;
        itemData = new JSONArray();
        link = new JSONArray();
        this.ver = ver;
    }

    private void addItem(ItemPo itemPo) {
        if (itemData.contains(itemPo.toJSONObject())) return;
        itemData.add(itemPo.toJSONObject());
    }

    public void addLink(TriplePo triplePo) {
        ItemPo h, r, t;
        h = itemMapper.getById(triplePo.head, ver);
        r = itemMapper.getById(triplePo.relation, ver);
        t = itemMapper.getById(triplePo.tail, ver);
        this.addItem(h);
        this.addItem(t);
        link.add(triplePo.toJSONObject(r));
    }

    public void addLinkCopy(TriplePo triplePo, Integer copyNum) {
        ItemPo h, r, t;
        h = itemMapper.getById(triplePo.head, ver);
        r = itemMapper.getById(triplePo.relation, ver);
        t = itemMapper.getById(triplePo.tail, ver);
        ItemPo itemCopy = new ItemPo(t.id + "_copy_" + copyNum, t.tableId, t.title, t.name, t.division, t.comment);
        this.addItem(h);
        this.addItem(itemCopy);
        TriplePo triCopy = new TriplePo(triplePo.tableId, h.id, r.id, itemCopy.id);
        link.add(triCopy.toJSONObject(r));
    }

}
