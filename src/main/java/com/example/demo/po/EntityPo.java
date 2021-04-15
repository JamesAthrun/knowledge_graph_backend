package com.example.demo.po;

import com.alibaba.fastjson.JSONObject;

public class EntityPo {
    public String recordId;
    public String id;
    public String nameEn;
    public String nameCn;
    public String division;
    public String from;
    public String comment;

    public EntityPo(String recordId,String id, String nameEn, String nameCn, String division, String from,String comment) {
        this.recordId = recordId;
        this.id = id;
        this.nameEn = nameEn;
        this.nameCn = nameCn;
        this.division = division;
        this.from = from;
        this.comment = comment;
    }

    public JSONObject toJSONObject(){
        JSONObject item = new JSONObject();
        item.put("id",this.recordId);
        String des = "";
        if(!this.nameCn.equals("")){
            item.put("name",this.nameCn);
            des += "英文名 "+this.nameEn+"\n";
        }
        else{
            if(!this.nameEn.equals(""))
                item.put("name",this.nameEn);
            else item.put("name",this.id);
        }
        if(!this.comment.equals("")) des += "评论 "+this.comment+"\n";
        item.put("des",des);
        switch (this.division) {
            case "String":
                item.put("category", "1");
                break;
            case "Class":
                item.put("category", "2");
                break;
            case "Resource":
                item.put("category", "3");
                break;
            default:
                item.put("category", "4");
        }
        return item;
    }

}
