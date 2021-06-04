package com.example.demo.po;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.Trans;

public class ItemPo {
    public String id;
    public String tableId;
    public String title;
    public String name;
    public String division;
    public String comment;
    public String ver;
    public String drop;

    public ItemPo(String id, String tableId, String title, String name, String division, String comment) {
        this.id = id;
        this.tableId = tableId;
        this.title = title;
        this.name = name;
        this.division = division;
        this.comment = comment;
    }

    public ItemPo(String id, String tableId, String title, String name, String division, String comment, String ver, String drop) {
        this.id = id;
        this.tableId = tableId;
        this.title = title;
        this.name = name;
        this.division = division;
        this.comment = comment;
        this.ver = ver;
        this.drop = drop;
    }

    public JSONObject toJSONObject() {
        JSONObject item = new JSONObject();
        item.put("id", this.id);

        item.put("text", getNormName());

        switch (this.division) {
            case "String":
                item.put("nodeshape", "1");
                break;
            case "Class":
                item.put("nodeshape", "2");
                break;
            case "Resource":
                item.put("nodeshape", "3");
                break;
            case "Property":
                item.put("nodeshape", "4");
                break;
            default:
                item.put("nodeshape", "1");
        }

        JSONObject data = Trans.javaObjectToJSONObject(this);
        item.put("data", data);

        return item;
    }

    protected String getNormName() {
        String tmp;
        if (!this.name.equals(""))
            tmp = this.name;
        else
            tmp = this.title;
        if (tmp.length() <= 10) return tmp;
        else return tmp.substring(0, 10) + "...";
    }

    protected String getFullName() {
        if (!this.name.equals("")) return name;
        else return title;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.name.equals("")) {
            sb.append(this.name).append("\n");
        } else {
            sb.append(this.title).append("\n");
        }
        if (!this.comment.equals("")) sb.append("评论 ").append(this.comment).append("\n");
        return sb.toString();
    }
}
