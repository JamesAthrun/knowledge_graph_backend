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
class UserGroupTests {

    @Autowired
    KGService kgService;
    @Autowired
    KGController kgController;
    @Autowired
    ItemMapper itemMapper;

    @Test
    void PermissionTest1() {
        Boolean writePermission1 = kgService.getWritePermission("0", 1);
        Boolean writePermission2 = kgService.getWritePermission("0", 2);
        assertEquals(writePermission1, true);
        assertEquals(writePermission2, false);
    }

    @Test
    void PermissionTest2() {
        kgService.changeTablePermission("0", 111);
        Boolean writePermission1 = kgService.getWritePermission("0", 1);
        Boolean writePermission2 = kgService.getWritePermission("0", 2);
        Boolean readPermission1 = kgService.getReadPermission("0", 1);
        Boolean readPermission2 = kgService.getReadPermission("0", 2);
        assertEquals(writePermission1, false);
        assertEquals(writePermission2, false);
        assertEquals(readPermission1, true);
        assertEquals(readPermission2, true);
        kgService.changeTablePermission("0", 222);
        Boolean writePermission3 = kgService.getWritePermission("0", 1);
        Boolean writePermission4 = kgService.getWritePermission("0", 2);
        assertEquals(writePermission3, true);
        assertEquals(writePermission4, true);
        kgService.changeTablePermission("0", 210);
        Boolean readPermission3 = kgService.getReadPermission("0", 1);
        Boolean readPermission4 = kgService.getReadPermission("0", 2);
        assertEquals(readPermission3, true);
        assertEquals(readPermission4, false);
    }

}
