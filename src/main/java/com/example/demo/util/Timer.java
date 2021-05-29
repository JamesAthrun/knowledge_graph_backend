package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Timer {
    long t;
    @Autowired
    GlobalLogger logger;

    Timer() {
        this.t = -1;
    }

    public static String getFormatTime() {
        return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
    }

    public void set() {
        if (t == -1) this.t = System.currentTimeMillis();
        else logger.error("timer set error");
    }

    public String get() {
        if (t == -1) {
            logger.error("timer get error");
            return "error";
        } else {
            String s = System.currentTimeMillis() - t + "ms";
            t = -1;
            return s;
        }
    }
}

