package com.example.demo.po;

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
}
