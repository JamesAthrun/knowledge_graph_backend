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

    @PostMapping("/commitChange")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean commitChange(@RequestBody KGEditFormVo f){
        logger.log("KGController commitChange");
        return kgService.commitChange(f);
    }

    @PostMapping("/cancelChange")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean cancelChange(@RequestBody KGEditFormVo f){
        logger.log("KGController cancelChange");
        return kgService.cancelChange(f);
    }

    @GetMapping("/confirmChange")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean confirmChange(@RequestParam String userName){
        logger.log("KGController confirmChange");
        return kgService.confirmChange(userName);
    }

    @GetMapping("/rollBackChange")
    @ApiOperation(
            value = "",
            notes = ""
    )
    public ResultBean rollBackChange(@RequestParam String ver){
        logger.log("KGController rollBackChange");
        return kgService.rollBackChange(ver);
    }

//    @PostMapping("/createItem")
//    @ApiOperation(
//            value = "",
//            notes = ""
//    )
//    public ResultBean createItem(@RequestBody KGEditFormVo f) {
//        logger.log("KGController createItem");
//        return kgService.createItem(
//                f.headId,
//                f.relationId,
//                f.tailId,
//                f.tableId,
//                f.title,
//                f.name,
//                f.division,
//                f.comment
//        );
//    }
//
//    @PostMapping("/createLink")
//    @ApiOperation(
//            value = "",
//            notes = ""
//    )
//    public ResultBean createLink(@RequestBody KGEditFormVo f) {
//        logger.log("KGController createLink");
//        return kgService.createLink(
//                f.tableId,
//                f.headId,
//                f.relationId,
//                f.tailId);
//    }
//
//    @PostMapping("/updateItem")
//    @ApiOperation(
//            value = "",
//            notes = ""
//    )
//    public ResultBean updateItem(@RequestBody KGEditFormVo f) {
//        logger.log("KGController updateItem");
//        return kgService.updateItem(
//                f.id,
//                f.tableId,
//                f.title,
//                f.name,
//                f.division,
//                f.comment
//        );
//    }
//
//    @PostMapping("/replaceItem")
//    @ApiOperation(
//            value = "",
//            notes = ""
//    )
//    public ResultBean replaceItem(@RequestBody KGEditFormVo f) {
//        logger.log("KGController replaceItem");
//        return kgService.replaceItem(
//                f.headId,
//                f.relationId,
//                f.tailId,
//                f.id,
//                f.tableId,
//                f.title,
//                f.name,
//                f.division,
//                f.comment
//        );
//    }
//
//    @PostMapping("/deleteItem")
//    @ApiOperation(
//            value = "",
//            notes = ""
//    )
//    public ResultBean deleteItem(@RequestBody KGEditFormVo f) {
//        logger.log("KGController deleteItem");
//        return kgService.deleteItem(f.id);
//    }
//
//    @PostMapping("/deleteLink")
//    @ApiOperation(
//            value = "",
//            notes = ""
//    )
//    public ResultBean deleteLink(@RequestBody KGEditFormVo f) {
//        logger.log("KGController deleteLink");
//        return kgService.deleteLink(
//                f.headId,
//                f.relationId,
//                f.tailId);
//    }

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
