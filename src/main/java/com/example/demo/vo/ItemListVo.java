package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.example.demo.po.ItemPo;

public class ItemListVo {

    public JSONArray data = new JSONArray();

    public void addItem(ItemPo itemPo) {
        data.add(itemPo.toJSONObject());
    }

}
