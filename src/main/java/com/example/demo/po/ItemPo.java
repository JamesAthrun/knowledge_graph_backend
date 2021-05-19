package com.example.demo.po;

import com.alibaba.fastjson.JSONObject;

public class ItemPo {
    public String id;
    public String tableId;
    public String title;
    public String name;
    public String division;
    public String comment;

    public ItemPo(String id, String tableId, String title, String name, String division, String comment) {
        this.id = id;
        this.tableId = tableId;
        this.title = title;
        this.name = name;
        this.division = division;
        this.comment = comment;
    }

    public JSONObject toJSONObject() {
        JSONObject item = new JSONObject();
        item.put("id", this.id);
        String des = "";
        if (!this.name.equals("")) item.put("text", this.name);
        else item.put("text", this.title);
        if (!this.comment.equals("")) des += "评论 " + this.comment + "\n";
        item.put("des", des);
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
        return item;
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
