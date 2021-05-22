package com.example.demo.vo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.data.KG.ItemMapper;
import com.example.demo.po.ItemPo;

import java.util.ArrayList;

public class TreeInfoVo {
    private final String root;
    private final ItemMapper itemMapper;
    private final String ver;
    public ArrayList<JSONObject> nodes;

    public TreeInfoVo(String r, ItemMapper i, String ver) {
        this.root = r;
        this.itemMapper = i;
        nodes = new ArrayList<>();
        this.ver = ver;
    }

    public JSONObject getNode(String id) {
        for (JSONObject item : nodes) {
            if (item.getString("id").equals(id)) return item;
        }
        return createNode(id);
    }

    private JSONObject createNode(String id) {
        JSONObject jo = new JSONObject();
        jo.put("id", id);
        ItemPo i = itemMapper.getById(id, ver);
        jo.put("name", i.toString());
        jo.put("children", new JSONArray());
        nodes.add(jo);
        return jo;
    }

    public JSONObject addItem(String parentId, String childId) {
        JSONObject child = createNode(childId);
        getNode(parentId).getJSONArray("children").add(child);
        return child;
    }

    public void itemAdd(JSONObject parent, String childId) {
        JSONObject child = createNode(childId);
        parent.getJSONArray("children").add(child);
    }

    public JSONObject getRoot() {
        return getNode(root);
    }

}
