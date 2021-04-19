package com.example.demo;

import com.example.demo.bl.KG.KGService;
import com.example.demo.util.ResultBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class KGTests {

    @Autowired
    KGService kgService;

    @Test
    void searchEntity1() {
        ResultBean res = kgService.searchEntity("佩戴");
        assertEquals(res.code, 1);
        assertNotEquals(res.data, "{\"data\":[]}");
    }

    @Test
    void searchEntity2() {
        ResultBean res = kgService.searchEntity("__#+1%&^9");
        assertEquals(res.code, 1);
        assertEquals(res.data, "{\"data\":[]}");
    }

    @Test
    void getGraphData1() {
        ResultBean res = kgService.getGraphData("103774");
        assertEquals(res.code, 1);
        assertNotEquals(res.data, "{\"data\":[]}");
    }

    @Test
    void getGraphData2() {
        ResultBean res = kgService.getGraphData("103775");
        assertEquals(res.code, 1);
        assertNotEquals(res.data, "{\"data\":[]}");
    }
}
