package com.example.demo.po;

public class PropertyPO {
    public String recordId;
    public String id;
    public String nameEn;
    public String nameCn;
    public String domain;
    public String range;
    public String from;
    public String comment;

    public PropertyPO(String recordId, String id, String nameEn, String nameCn, String domain, String range, String from,String comment) {
        this.recordId = recordId;
        this.id = id;
        this.nameEn = nameEn;
        this.nameCn = nameCn;
        this.domain = domain;
        this.range = range;
        this.from = from;
        this.comment = comment;
    }
}
