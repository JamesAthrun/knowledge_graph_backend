package com.example.demo;

import com.example.demo.util.KGManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.demo.data")
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        KGManager gm = new KGManager("src/main/resources/covid-19-prevention-2020-03-11.json");

        //SpringApplication.run(DemoApplication.class, args);
    }
}
