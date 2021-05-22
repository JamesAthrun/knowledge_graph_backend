package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class GlobalLogger {

    final Logger logger;

    public GlobalLogger() {
        this.logger = Logger.getLogger("GlobalLogger");
//        this.logger.addHandler(new ConsoleHandler());
    }

    public void log(String info) {
        logger.log(Level.INFO, info);
    }

    public void error(String info) {
        logger.log(Level.INFO, info);
    }

}
