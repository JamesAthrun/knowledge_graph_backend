package com.example.demo;

import com.example.demo.bl.KG.KGService;
import com.example.demo.data.KG.GraphMapper;
import com.example.demo.data.KG.ItemMapper;
import com.example.demo.util.GlobalConfigure;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import com.example.demo.vo.KGEditFormVo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KGTests {

    @Autowired
    KGService kgService;
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    GlobalConfigure config;
    @Autowired
    GlobalLogger logger;
    @Autowired
    GraphMapper graphMapper;
    
    @AfterEach
    public void initAll(){
        logger.log("init");
        config.init();
    }

    @Test
    public void searchEntity1() {
        ResultBean res = kgService.searchEntity("佩戴", "0");
        assertEquals(res.code, 1);
        assertNotEquals(res.data, "{\"data\":[]}");
    }

    @Test
    public void searchEntity2() {
        ResultBean res = kgService.searchEntity("__#+1%&^9", "0");
        assertEquals(res.code, 1);
        assertEquals(res.data, "{\"data\":[]}");
    }

    @Test
    public void getGraphData1() {
        ResultBean res = kgService.getGraphData("19185962", "0");
        assertEquals(res.code, 1);
        assertNotEquals(res.data, "{\"data\":[]}");
    }

    @Test
    public void getGraphData2() {
        ResultBean res = kgService.getGraphData("19185962", "0");
        assertEquals(res.code, 1);
        assertNotEquals(res.data, "{\"data\":[]}");
    }

    @Test
    public void confirmTest1() {
        KGEditFormVo f1 = new KGEditFormVo("19246962", "19255764", "1", "1", "0", "NEW1", "新实体一", "String", "", "createItem", "xgs");
        KGEditFormVo f2 = new KGEditFormVo("1", "19255764", "2", "2", "0", "NEW2", "新实体二", "String", "", "createItem", "xgs");

        assertEquals(1,kgService.commitChange(f1).code);
        assertEquals(1,kgService.commitChange(f2).code);
        assertEquals(1,kgService.confirmChange("xgs").code);
        assertNotNull(itemMapper.getByTitle("NEW1", "1"));
        assertNotNull(itemMapper.getByTitle("NEW2", "1"));
    }

    @Test
    public void confirmTest2() {
        KGEditFormVo f1 = new KGEditFormVo("19246962", "19255764", "19613276", "19613276", "0", "REP1", "替换实体一", "String", "", "replaceItem", "xgs");
        KGEditFormVo f2 = new KGEditFormVo("19246962", "19255764", "1", "19255764", "0", "REP2", "替换实体二", "String", "", "replaceItem", "xgs");

        assertEquals(1,kgService.commitChange(f1).code);
        assertEquals(1,kgService.commitChange(f2).code);
        assertEquals(1,kgService.confirmChange("xgs").code);
        assertNotNull(itemMapper.getByTitle("REP1", "1"));
        assertNotNull(itemMapper.getByTitle("REP2", "1"));
    }

    @Test
    public void cancelTest() {
        KGEditFormVo f1 = new KGEditFormVo("19246962", "19255764", "19613276", "19613276", "0", "REP1", "替换实体一", "String", "", "replaceItem", "xgs");
        KGEditFormVo f2 = new KGEditFormVo("19246962", "19255764", "1", "19255764", "0", "REP2", "替换实体二", "String", "", "replaceItem", "xgs");

        assertEquals(1,kgService.commitChange(f1).code);
        assertEquals(1,kgService.commitChange(f2).code);
        assertEquals(1,kgService.cancelChange("xgs").code);
        assertEquals(1,kgService.confirmChange("xgs").code);
        assertNotNull(itemMapper.getByTitle("REP1", "1"));
        assertNull(itemMapper.getByTitle("REP2", "1"));
    }


    @Test
    public void historyTest1() {
        KGEditFormVo f1 = new KGEditFormVo("19246962", "19255764", "1", "1", "0", "NEW1", "新实体一", "String", "", "createItem", "xgs");
        KGEditFormVo f2 = new KGEditFormVo("1", "19255764", "2", "2", "0", "NEW2", "新实体二", "String", "", "createItem", "xgs");

        assertEquals(1,kgService.commitChange(f1).code);
        assertEquals(1,kgService.commitChange(f2).code);
        assertEquals(1,kgService.confirmChange("xgs").code);
        ResultBean res = kgService.getGraphHistory("0");
        assertNotNull(res.data);
        assertEquals(1, res.code);
        logger.log(res.data);
    }

    @Test
    public void historyTest2() throws InterruptedException {
        KGEditFormVo f1 = new KGEditFormVo("19246962", "19255764", "1", "1", "0", "NEW1", "新实体一", "String", "", "createItem", "xgs");
        KGEditFormVo f2 = new KGEditFormVo("1", "19255764", "2", "2", "0", "NEW2", "新实体二", "String", "", "createItem", "xgs");

        assertEquals(1,kgService.commitChange(f1).code);
        assertEquals(1,kgService.commitChange(f2).code);
        assertEquals(1,kgService.confirmChange("xgs").code);
        Thread.sleep(3000);
        kgService.rollBackChange("0", "0");
        ResultBean res = kgService.getGraphHistory("0");
        assertEquals(1, res.code);
        assertEquals("0",graphMapper.getPresentVer("0"));
        logger.log(res.data);
    }

    @Test
    public void askTest(){
        ResultBean res = kgService.ask("农民工 预防");
        assertEquals(1,res.code);
        assertNotNull(res.data);
        logger.log(res.data);
    }
}
