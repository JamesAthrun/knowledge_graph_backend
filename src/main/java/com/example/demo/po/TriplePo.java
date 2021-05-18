package com.example.demo.po;

import com.alibaba.fastjson.JSONObject;

public class TriplePo {
    public String tableId;
    public String head;
    public String relation;
    public String tail;

    public TriplePo(String tableId, String head, String relation, String tail) {
        this.tableId = tableId;
        this.head = head;
        this.relation = relation;
        this.tail = tail;
    }

    public JSONObject toJSONObject(String name) {
        JSONObject item = new JSONObject();
        item.put("text", name);
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
}
