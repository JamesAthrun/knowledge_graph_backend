package com.example.demo;

import com.example.demo.bl.KG.KGService;
import com.example.demo.controller.KG.KGController;
import com.example.demo.data.KG.ItemMapper;
import com.example.demo.util.ResultBean;
import com.example.demo.vo.KGEditFormVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KGTests {

    @Autowired
    KGService kgService;
    @Autowired
    KGController kgController;
    @Autowired
    ItemMapper itemMapper;

    @Test
    void searchEntity1() {
        ResultBean res = kgService.searchEntity("佩戴", "0");
        assertEquals(res.code, 1);
        assertNotEquals(res.data, "{\"data\":[]}");
    }

    @Test
    void searchEntity2() {
        ResultBean res = kgService.searchEntity("__#+1%&^9", "0");
        assertEquals(res.code, 1);
        assertEquals(res.data, "{\"data\":[]}");
    }

    @Test
    void getGraphData1() {
        ResultBean res = kgService.getGraphData("19185962", "0");
        assertEquals(res.code, 1);
        assertNotEquals(res.data, "{\"data\":[]}");
    }

    @Test
    void getGraphData2() {
        ResultBean res = kgService.getGraphData("19185962", "0");
        assertEquals(res.code, 1);
        assertNotEquals(res.data, "{\"data\":[]}");
    }

    @Test
    void confirmTest1() {
        KGEditFormVo f1 = new KGEditFormVo("19246962", "19255764", "1", "1", "0", "NEW1", "新实体一", "String", "", "createItem", "xgs");
        KGEditFormVo f2 = new KGEditFormVo("1", "19255764", "2", "2", "0", "NEW2", "新实体二", "String", "", "createItem", "xgs");

        kgController.commitChange(f1);
        kgController.commitChange(f2);
        kgController.confirmChange("xgs");
        assertNotNull(itemMapper.getByTitle("NEW1", "1"));
        assertNotNull(itemMapper.getByTitle("NEW2", "1"));
    }

    @Test
    void confirmTest2() {
        KGEditFormVo f1 = new KGEditFormVo("19246962", "19255764", "19613276", "19613276", "0", "REP1", "替换实体一", "String", "", "replaceItem", "xgs");
        KGEditFormVo f2 = new KGEditFormVo("19246962", "19255764", "1", "19255764", "0", "REP2", "替换实体二", "String", "", "replaceItem", "xgs");

        kgController.commitChange(f1);
        kgController.commitChange(f2);
        kgController.confirmChange("xgs");
        assertNotNull(itemMapper.getByTitle("REP1", "1"));
        assertNotNull(itemMapper.getByTitle("REP2", "1"));
    }
}
