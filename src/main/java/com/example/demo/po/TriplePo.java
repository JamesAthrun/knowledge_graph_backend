package com.example.demo.po;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class TriplePo {
    public String tableId;
    public String head;
    public String relation;
    public String tail;
    public String ver;
    public String drop;

    public TriplePo(String tableId, String head, String relation, String tail) {
        this.tableId = tableId;
        this.head = head;
        this.relation = relation;
        this.tail = tail;
    }

    public TriplePo(String tableId, String head, String relation, String tail, String ver, String drop) {
        this.tableId = tableId;
        this.head = head;
        this.relation = relation;
        this.tail = tail;
        this.ver = ver;
        this.drop = drop;
    }

    public JSONObject toJSONObject(ItemPo property) {
        JSONObject item = new JSONObject();

        item.put("text", property.getNormName());

        JSONObject data = new JSONObject();
        data.put("content", property.getFullName());

        item.put("data", data);
        item.put("from", this.head);
        item.put("id", this.relation);
        item.put("to", this.tail);
        return item;
    }

    public JSONObject toJSONObject() {
        JSONObject item = new JSONObject();
        item.put("text", this.relation);
        item.put("from", this.head);
        item.put("id", this.relation);
        item.put("to", this.tail);
        return item;
    }

    public boolean existIn(List<TriplePo> list) {
        for (TriplePo tmp : list) {
            if (tmp.head.equals(head) && tmp.relation.equals(relation) && tmp.tail.equals(tail))
                return true;
        }
        return false;
    }
}
