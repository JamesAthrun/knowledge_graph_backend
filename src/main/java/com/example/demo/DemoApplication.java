package com.example.demo;

import com.example.demo.util.KGManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@MapperScan("com.example.demo.data")
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {

        KGManager gm = new KGManager("src/main/resources/covid-19-prevention-2020-03-11.json");
        List<String[]> tri_id = gm.getTripleSetById();
        List<String[]> tri_num = gm.getTripleSetByNum();
        List<String[]> num_id_map = gm.getNumIdMap();

        //SpringApplication.run(DemoApplication.class, args);
    }
}
