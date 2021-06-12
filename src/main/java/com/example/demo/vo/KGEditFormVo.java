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
        String detailStr = "";
        if (mode == 0) {
            switch (op){
                case "createItem":
                    detailStr+= String.format("create item (id, title, name)->(「%s」,「%s」,「%s」)", id,title,name);
                    break;
                case "createLink":
                    detailStr+= String.format("link items in triple(「%s」,「%s」,「%s」)", headId,relationId,tailId);
                    break;
                case "updateItem":
                    detailStr+= String.format("update item 「%s」 in all triples", id);
                    break;
                case "replaceItem":
                    detailStr+=String.format("replace item 「%s」 in triple (「%s」,「%s」,「%s」)", id,headId,relationId,tailId);
                    break;
                case "deleteItem":
                    detailStr+=String.format("delete item 「%s」", id);
                    break;
                case "deleteLink":
                    detailStr+=String.format("unlink items in triple(「%s」,「%s」,「%s」)",headId,relationId,tailId);
                    break;
                default:
                    ;
                }
                JSONObject jo = new JSONObject();
                jo.put("detail",detailStr);
                return jo;
            }
        return null;
    }
}
