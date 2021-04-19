package com.example.demo.controller.KG;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bl.KG.KGService;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/KG")
@Api(
        value = "知识图谱相关，增删查改",
        tags = "知识图谱"
)
public class KGController {
    @Autowired
    KGService kgService;
    @Autowired
    GlobalLogger logger;

    @GetMapping("/search")
    @ApiOperation(
            value = "接受一个关键词，返回匹配的item列表",
            notes = "匹配的范围包括编号、中英文名、字符串内容"
    )
    public ResultBean search(@RequestParam String keywords) {
        logger.log("KGController search");
        return kgService.searchEntity(keywords);
    }

    @GetMapping("/getGraphData")
    @ApiOperation(
            value = "接受一个节点的id，返回该节点所在的知识图谱局部的信息",
            notes = "孤立节点的返回是不正常的"
    )
    public ResultBean getGraphData(@RequestParam String id) {
        logger.log("KGController getGraphData");
        return kgService.getGraphData(id);
    }

    @GetMapping("/getTreeData")
    @ApiOperation(
            value = "和getGraphData类似，不过返回信息是树形结构",
            notes = ""
    )
    public ResultBean getTreeData(@RequestParam String id) {
        logger.log("KGController getTreeData");
        return kgService.getTreeData(id);
    }

    @PostMapping("/createGraphByJsonStr")
    @ApiOperation(
            value = "通过一个jsonStr创建一张知识图谱",
            notes = ""
    )
    public ResultBean createGraphByJsonStr(@RequestBody String jsonStr) {
        logger.log("KGController createGraphByJsonStr");
        return kgService.createGraphByJsonStr(jsonStr);
    }

    @PostMapping("/uploadFile")
    @ApiOperation(
            value = "上传文件时用到的假接口",
            notes = ""
    )
    public ResultBean uploadFile(HttpServletRequest request) {
        logger.log("uploading");
        return ResultBean.success();
    }

    @PostMapping("/createEntity")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean createEntity(@RequestBody String jsonStr) {
        logger.log("KGController createEntity");
        JSONObject jo = JSON.parseObject(jsonStr);
        return kgService.createEntity(
                jo.getString("headId"),
                jo.getString("relationId"),
                jo.getString("tailId"),
                jo.getString("name"),
                jo.getString("comment"),
                jo.getString("nameEn"),
                jo.getString("nameCn"),
                jo.getString("division"),
                jo.getString("from")
        );
    }

    @PostMapping("/createProperty")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean createProperty(@RequestBody String jsonStr) {
        logger.log("KGController createProperty");
        JSONObject jo = JSON.parseObject(jsonStr);
        return kgService.createProperty(
                jo.getString("id"),
                jo.getString("comment"),
                jo.getString("nameEn"),
                jo.getString("nameCn"),
                jo.getString("from"),
                jo.getString("domain"),
                jo.getString("range"));
    }

    @PostMapping("/createLink")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean createLink(@RequestBody String jsonStr) {
        logger.log("KGController createLink");
        JSONObject jo = JSON.parseObject(jsonStr);
        return kgService.createLink(
                jo.getString("headId"),
                jo.getString("relationId"),
                jo.getString("tailId"));
    }

    @PostMapping("/updateItem")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean updateItem(@RequestBody String jsonStr) {
        logger.log("KGController updateItem");
        JSONObject jo = JSON.parseObject(jsonStr);
        return kgService.updateItem(
                jo.getString("id"),
                jo.getString("comment"),
                jo.getString("nameEn"),
                jo.getString("nameCn"),
                jo.getString("division"),
                jo.getString("from"),
                jo.getString("domain"),
                jo.getString("range"));
    }

    @PostMapping("/replaceItem")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean replaceItem(@RequestBody String jsonStr) {
        logger.log("KGController replaceItem");
        JSONObject jo = JSON.parseObject(jsonStr);
        return kgService.replaceItem(
                jo.getString("id"),
                jo.getString("headId"),
                jo.getString("relationId"),
                jo.getString("tailId"),
                jo.getString("name"),
                jo.getString("comment"),
                jo.getString("nameEn"),
                jo.getString("nameCn"),
                jo.getString("division"),
                jo.getString("from"),
                jo.getString("domain"),
                jo.getString("range"));
    }

    @PostMapping("/deleteItem")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean deleteItem(@RequestBody String id) {
        logger.log("KGController deleteItem");
        return kgService.deleteItem(id);
    }

    @PostMapping("/deleteLink")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean deleteLink(@RequestBody String jsonStr) {
        logger.log("KGController deleteLink");
        JSONObject jo = JSON.parseObject(jsonStr);
        return kgService.deleteLink(
                jo.getString("headId"),
                jo.getString("relationId"),
                jo.getString("tailId"));
    }

    @PostMapping("/ask")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean ask(@RequestBody String questionStr) {
        logger.log("KGController ask");
        return kgService.ask(questionStr);
    }

}
