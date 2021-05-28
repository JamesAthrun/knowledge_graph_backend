package com.example.demo.controller.KG;

import com.example.demo.bl.KG.KGService;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import com.example.demo.vo.KGEditFormVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public ResultBean search(@RequestParam String keywords, @RequestParam String ver) {
        logger.log("KGController search");
        return kgService.searchEntity(keywords, ver);
    }

    @GetMapping("/getGraphData")
    @ApiOperation(
            value = "接受一个节点的id，返回该节点所在的知识图谱局部的信息",
            notes = "孤立节点的返回是不正常的"
    )
    public ResultBean getGraphData(@RequestParam String id, @RequestParam String ver) {
        logger.log("KGController getGraphData");
        return kgService.getGraphData(id, ver);
    }

    @GetMapping("/getTreeData")
    @ApiOperation(
            value = "和getGraphData类似，不过返回信息是树形结构",
            notes = ""
    )
    public ResultBean getTreeData(@RequestParam String id, @RequestParam String ver) {
        logger.log("KGController getTreeData");
        return kgService.getTreeData(id, ver);
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

    @PostMapping("/commitChange")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean commitChange(@RequestBody KGEditFormVo f) {
        logger.log("KGController commitChange");
        return kgService.commitChange(f);
    }

    @PostMapping("/cancelChange")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean cancelChange(@RequestBody KGEditFormVo f) {
        logger.log("KGController cancelChange");
        return kgService.cancelChange(f);
    }

    @GetMapping("/confirmChange")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean confirmChange(@RequestParam String userName) {
        logger.log("KGController confirmChange");
        return kgService.confirmChange(userName);
    }

    @GetMapping("/rollBackChange")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean rollBackChange(@RequestParam String ver, @RequestParam String tableId) {
        logger.log("KGController rollBackChange");
        return kgService.rollBackChange(ver, tableId);
    }

    @PostMapping("/ask")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean ask(HttpServletRequest request, @RequestBody String questionStr) {
        logger.log("KGController ask");
        return kgService.ask(questionStr);
    }

    @GetMapping("/getGraphInfo")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean getGraphInfo(@RequestParam String tableId) {
        logger.log("KGController rollBackChange");
        return kgService.getGraphInfo(tableId);
    }

    @GetMapping("/getAllGraphInfo")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean getAllGraphInfo() {
        logger.log("KGController rollBackChange");
        return kgService.getAllGraphInfo();
    }

}
