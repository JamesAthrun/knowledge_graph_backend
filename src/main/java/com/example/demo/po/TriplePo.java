package com.example.demo.po;

public class TriplePo {
    public int recordId = 0;
    public String head;
    public String relation;
    public String tail;

    public TriplePo(String head, String relation, String tail) {
        this.head = head;
        this.relation = relation;
        this.tail = tail;
    }
}