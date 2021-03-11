package com.example.demo.util;

import java.util.ArrayList;
import java.util.List;

public class KGNode {
    public String id;
    public List<KGNode> relations;
    public List<KGNode> tails;

    public KGNode(String id){
        this.id = id;
        this.relations = new ArrayList<>();
        this.tails = new ArrayList<>();
    }

    public void addCouple(KGNode relation,KGNode tail){
        this.relations.add(relation);
        this.tails.add(tail);
    }
}
