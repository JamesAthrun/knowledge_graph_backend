package com.example.demo.po;

public class GraphPo {
    public String tableId;
    public String name;
    public String description;
    public String ver;
    public int userId;
    public int groupId;
    public int authority;

    public GraphPo(String tableId, String name, String description, String ver, int userId, int groupId, int authority) {
        this.tableId = tableId;
        this.name = name;
        this.description = description;
        this.ver = ver;
        this.userId = userId;
        this.groupId = groupId;
        this.authority = authority;
    }
}
