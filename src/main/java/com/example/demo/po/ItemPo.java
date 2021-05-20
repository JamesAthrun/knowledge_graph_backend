package com.example.demo.po;

import com.alibaba.fastjson.JSONObject;

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
        JSONObject data = new JSONObject();
        if (!this.name.equals("")){
            if(name.contains(" ")) {
                item.put("text",getNameSplit()[0]);
                data.put("nameEn",getNameSplit()[1]);
            }
            else {
                item.put("text", this.name);
                data.put("nameEn","");
            }
        }
        else {
            if(!this.division.equals("String"))
                item.put("text","");
            else {
                if (this.title.length() < 10)
                    item.put("text", this.title);
                else
                    item.put("text", this.title.substring(0, 10) + "...");
            }
            data.put("content", this.title);
        }
        item.put("data",data);

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

    public String[] getNameSplit(){
        String[] splits = name.split(" ");
        String nameCn = splits[splits.length-1];
        StringBuilder nameEn = new StringBuilder();
        for(int i=0;i<splits.length-1;i++)
            nameEn.append(splits[i]);
        return new String[]{nameCn,nameEn.toString().replace("_"," ")};
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
