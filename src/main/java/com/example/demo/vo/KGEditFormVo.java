package com.example.demo.vo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.Trans;

public class KGEditFormVo {
    public String headId;
    public String relationId;
    public String tailId;

    public String id;
    public String tableId;
    public String title;
    public String name;
    public String division;
    public String comment;

    public String op;
    public String user;

    public KGEditFormVo() {
    }

    public KGEditFormVo(String headId, String relationId, String tailId, String id, String tableId, String title, String name, String division, String comment, String op, String user) {
        this.headId = headId;
        this.relationId = relationId;
        this.tailId = tailId;
        this.id = id;
        this.tableId = tableId;
        this.title = title;
        this.name = name;
        this.division = division;
        this.comment = comment;
        this.op = op;
        this.user = user;
    }

    public JSONObject toJSONObject() {
        return Trans.javaObjectToJSONObject(this);
    }

    public JSONObject toJSONObject(Integer mode) {
        if (mode == 0) {
            //todo 具体的翻译规则
            return Trans.javaObjectToJSONObject(this);
        }
        return null;
    }
}
