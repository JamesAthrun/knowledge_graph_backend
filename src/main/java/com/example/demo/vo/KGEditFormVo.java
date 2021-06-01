package com.example.demo.vo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.Recorder;
import com.example.demo.util.Trans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    public static HashMap<KGEditFormVo, String> handleId(List<KGEditFormVo> fs, Recorder recorder) {
        List<String> idToMap = new ArrayList<>();
        HashMap<KGEditFormVo, String> replaceMap = new HashMap<>();
        for (KGEditFormVo f : fs) {
            String[] ids = {f.headId, f.relationId, f.tailId, f.id};
            idToMap.addAll(Arrays.asList(ids));
            if (f.op.equals("replaceItem")) {
                replaceMap.put(f, f.getReplacePosition());
            }
        }

        HashMap<String, String> idMap = new HashMap<>();
        for (String key : idToMap) {
            if (!key.equals("") && Integer.parseInt(key) <= 1000) idMap.put(key, recorder.getRecordId());
        }

        for (KGEditFormVo f : fs) {
            if (idMap.containsKey(f.headId))
                f.headId = idMap.get(f.headId);
            if (idMap.containsKey(f.relationId))
                f.relationId = idMap.get(f.relationId);
            if (idMap.containsKey(f.tailId))
                f.tailId = idMap.get(f.tailId);
            if (idMap.containsKey(f.id))
                f.id = idMap.get(f.id);
        }
        return replaceMap;
    }

    public JSONObject toJSONObject() {
        return Trans.javaObjectToJSONObject(this);
    }

    public JSONObject toJSONObject(Integer mode) {
        if (mode == 0) {
            //todo 具体的翻译规则
            return Trans.javaObjectToJSONObject(this);
        }
        return null;
    }

    private String getReplacePosition() {
        if (id.equals(headId))
            return "head";
        else if (id.equals(relationId))
            return "relation";
        else if (id.equals(tailId))
            return "tail";
        else return null;
    }
}
