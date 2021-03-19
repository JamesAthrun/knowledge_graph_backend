package com.example.demo.po;

public class TriplePo {
    public String tableId;
    public String head;
    public String relation;
    public String tail;

    public TriplePo(String tableId, String head, String relation, String tail) {
        this.tableId = tableId;
        this.head = head;
        this.relation = relation;
        this.tail = tail;
    }
}
