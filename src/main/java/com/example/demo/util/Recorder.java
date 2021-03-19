package com.example.demo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Recorder {
    private int i;
    private int tableNum;
    private final int base;
    private final int leap;
    private final int step;

    public Recorder(int i, int tableNum, int base, int leap, int step) {
        this.i = i;
        this.tableNum = tableNum;
        this.base = base;
        this.leap = leap;
        this.step = step;
    }

    public Recorder(){
        this.i = -1;
        this.tableNum = -1;
        this.base = 86400;
        this.leap = 101;
        this.step = 17;
    }

    public String getRecordId(){
        this.i += 1;
        return String.valueOf(base+this.i*leap+this.i%step);
    }

    @Bean
    public Recorder initRecorder(){
        return new Recorder();
    }

    public String getTableId(){
        this.tableNum += 1;
        return String.valueOf(this.tableNum);
    }
}
