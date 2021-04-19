package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class Recorder {
    private int currentTableId;
    private int currentSeed;
    private int seed;
    private int base;
    private int A;
    private int B;
    private int M;


    public Recorder(int currentTableId, int seed, int base, int a, int b, int m) {
        this.currentTableId = currentTableId;
        this.seed = seed;
        this.currentSeed = this.seed;
        this.base = base;
        this.A = a;
        this.B = b;
        this.M = m;
    }

    public void init() {
        this.currentTableId = -1;
        this.seed = 1453;
        this.currentSeed = seed;
        this.base = 19181107;
        this.A = 2;
        this.B = 1949;
        this.M = 19911225 - 19181107;
    }

    public Recorder() {
        load();
    }

    public String getRecordId() {
        currentSeed = (A * currentSeed + B) % M;
        return String.valueOf(base + currentSeed);
    }

    public String getTableId() {
        this.currentTableId += 1;
        return String.valueOf(this.currentTableId);
    }

    public void load() {
        JSONObject jo = JSONObject.parseObject(GlobalTrans.getJsonString("src/main/resources/recorder.json"));
        currentTableId = jo.getInteger("currentTableId");
        currentSeed = jo.getInteger("currentSeed");
        seed = jo.getInteger("seed");
        base = jo.getInteger("base");
        A = jo.getInteger("A");
        B = jo.getInteger("B");
        M = jo.getInteger("M");
    }

    public void save() {
        JSONObject jo = new JSONObject();
        jo.put("currentTableId", currentTableId);
        jo.put("currentSeed", currentSeed);
        jo.put("seed", seed);
        jo.put("base", base);
        jo.put("A", A);
        jo.put("B", B);
        jo.put("M", M);
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter("src/main/resources/recorder.json"));
            out.write(jo.toJSONString());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
