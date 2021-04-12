package com.example.demo.util;

import org.springframework.stereotype.Component;

@Component
public class Recorder {
    private int currentTableId;
    private int currentSeed;
    private final int seed;
    private final int base;
    private final int A;
    private final int B;
    private final int M;


    public Recorder(int currentTableId, int seed, int base, int a, int b, int m) {
        this.currentTableId = currentTableId;
        this.seed = seed;
        this.currentSeed = this.seed;
        this.base = base;
        this.A = a;
        this.B = b;
        this.M = m;
    }

    public Recorder(){
        this.currentTableId = -1;
        this.seed = 1453;
        this.currentSeed = seed;
        this.base = 19181107;
        this.A = 2;
        this.B = 1949;
        this.M = 19911225-19181107;
    }

    public String getRecordId(){
        currentSeed = (A * currentSeed + B) % M;
        return String.valueOf(base+currentSeed);
    }

    public String getTableId(){
        this.currentTableId += 1;
        return String.valueOf(this.currentTableId);
    }
}
