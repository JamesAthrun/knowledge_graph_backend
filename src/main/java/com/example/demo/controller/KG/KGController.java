package com.example.demo.controller.KG;

import com.example.demo.anno.AuthAnno;
import com.example.demo.anno.AuthTableIdAnno;
import com.example.demo.anno.AuthUserNameAnno;
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
        value = "",
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
            notes = "匹配的范围包括编号、中英文名、字符串内容" +
                    "\n权限：任何用户"
    )
    @AuthAnno(level = "")
    public ResultBean search(HttpServletRequest request, @RequestParam String keywords, @RequestParam String ver) {
        logger.log("KGController search");
        return kgService.searchEntity(keywords, ver);
    }

    @GetMapping("/getGraphData")
    @ApiOperation(
            value = "接受一个节点的id，返回该节点所在的知识图谱局部的信息",
            notes = "孤立节点的返回是不正常的" +
                    "\n权限：读"
    )
    @AuthAnno(level = "r")
    public ResultBean getGraphData(HttpServletRequest request, @RequestParam String id, @RequestParam String ver) {
        logger.log("KGController getGraphData");
        return kgService.getGraphData(id, ver);
    }

    @GetMapping("/getTreeData")
    @ApiOperation(
            value = "和getGraphData类似，不过返回信息是树形结构",
            notes = "权限：读"
    )
    @AuthAnno(level = "r")
    public ResultBean getTreeData(@RequestParam String id, @RequestParam String ver) {
        logger.log("KGController getTreeData");
        return kgService.getTreeData(id, ver);
    }

    @PostMapping("/createGraphByJsonStr")
    @ApiOperation(
            value = "通过一个jsonStr创建一张知识图谱",
            notes = "权限：任何用户"
    )
    @AuthAnno(level = "")
    public ResultBean createGraphByJsonStr(@RequestBody String jsonStr, @AuthUserNameAnno String userName) {
        logger.log("KGController createGraphByJsonStr");
        return kgService.createGraphByJsonStr(jsonStr, userName);
    }

    @PostMapping("/uploadFile")
    @ApiOperation(
            value = "上传文件时用到的假接口",
            notes = "权限：游客"
    )
    public ResultBean uploadFile(HttpServletRequest request) {
        logger.log("uploading");
        return ResultBean.success();
    }

    @PostMapping("/commitChange")
    @ApiOperation(
            value = "",
            notes = "权限：写"
    )
    @AuthAnno(level = "w")
    public ResultBean commitChange(HttpServletRequest request, @AuthUserNameAnno String userName, @RequestBody KGEditFormVo f) {
        logger.log("KGController commitChange");
        if(!userName.equals(f.user)) return ResultBean.error(-111,"invalid user");
        return kgService.commitChange(f);
    }

    @GetMapping("/cancelChange")
    @ApiOperation(
            value = "",
            notes = "权限：写"
    )
    @AuthAnno(level = "w")
    public ResultBean cancelChange(HttpServletRequest request, @AuthUserNameAnno String userName) {
        logger.log("KGController cancelChange");
        return kgService.cancelChange(userName);
    }

    @GetMapping("/confirmChange")
    @ApiOperation(
            value = "",
            notes = "权限：写"
    )
    @AuthAnno(level = "w")
    public ResultBean confirmChange(HttpServletRequest request, @AuthUserNameAnno String userName) {
        logger.log("KGController confirmChange");
        return kgService.confirmChange(userName);
    }

    @GetMapping("/rollBackChange")
    @ApiOperation(
            value = "",
            notes = "权限：写"
    )
    @AuthAnno(level = "w")
    public ResultBean rollBackChange(HttpServletRequest request, @RequestParam String ver, @AuthTableIdAnno String tableId) {
        logger.log("KGController rollBackChange");
        return kgService.rollBackChange(ver, tableId);
    }

    @PostMapping("/ask")
    @ApiOperation(
            value = "",
            notes = "权限：读"
    )
    @AuthAnno(level = "r")
    public ResultBean ask(HttpServletRequest request, @RequestBody String questionStr) {
        logger.log("KGController ask");
        return kgService.ask(questionStr);
    }

    @GetMapping("/getGraphInfo")
    @ApiOperation(
            value = "",
            notes = "权限：读"
    )
    @AuthAnno(level = "r")
    public ResultBean getGraphInfo(HttpServletRequest request, @AuthTableIdAnno String tableId) {
        logger.log("KGController getGraphInfo");
        return kgService.getGraphInfo(tableId);
    }

    @GetMapping("/getAllGraphInfo")
    @ApiOperation(
            value = "",
            notes = "权限：任何用户"
    )
    @AuthAnno(level = "")
    public ResultBean getAllGraphInfo(HttpServletRequest request) {
        logger.log("KGController getAllGraphInfo");
        return kgService.getAllGraphInfo();
    }

    @GetMapping("/getGraphHistory")
    @ApiOperation(
            value = "",
            notes = "权限：读"
    )
    @AuthAnno(level = "r")
    public ResultBean getGraphHistory(HttpServletRequest request, @AuthTableIdAnno String tableId) {
        logger.log("KGController getGraphHistory");
        return kgService.getGraphHistory(tableId);
    }

    @GetMapping("/updateGraphAuthority")
    @ApiOperation(
            value = "",
            notes = "权限：写"
    )
    @AuthAnno(level = "w")
    public ResultBean updateGraphAuthority(HttpServletRequest request, @AuthTableIdAnno String tableId, @RequestParam int authority) {
        return kgService.changeTablePermission(tableId, authority);
    }

}
