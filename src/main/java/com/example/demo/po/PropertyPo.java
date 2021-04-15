package com.example.demo.po;

import com.alibaba.fastjson.JSONObject;

public class PropertyPo {
    public String recordId;
    public String id;
    public String nameEn;
    public String nameCn;
    public String domain;
    public String range;
    public String from;
    public String comment;

    public PropertyPo(String recordId, String id, String nameEn, String nameCn, String domain, String range, String from, String comment) {
        this.recordId = recordId;
        this.id = id;
        this.nameEn = nameEn;
        this.nameCn = nameCn;
        this.domain = domain;
        this.range = range;
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
        if(!this.domain.equals("")) des += "定义域 "+ this.domain+"\n";
        if(!this.range.equals("")) des += "值域 "+ this.range+"\n";
        item.put("des",des);
        item.put("category","4");
        return item;
    }
}
