package com.example.demo.controller.KG;

import com.example.demo.bl.KG.KGService;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/KG")
public class KGController {
    @Autowired
    KGService kgService;
    @Autowired
    GlobalLogger logger;

    @GetMapping("/search")
    public ResultBean search(@RequestParam String keywords){
        logger.log("KGController search");
        return kgService.searchEntity(keywords);
    }

    @GetMapping("/getGraphData")
    public ResultBean getGraphData(@RequestParam String id){
        logger.log("KGController getGraphData");
        return kgService.getGraphData(id);
    }

    @GetMapping("/getTreeData")
    public ResultBean getTreeData(@RequestParam String id){
        logger.log("KGController getTreeData");
        //todo
        return null;
    }
    @PostMapping("/createGraphByJsonStr")
    public ResultBean createGraphByJsonStr(@RequestBody String jsonStr){
        logger.log("KGController createGraphByJsonStr");
        return kgService.createGraphByJsonStr(jsonStr);
    }

    @PostMapping("/uploadFile")
    public ResultBean uploadFile(HttpServletRequest request){
        logger.log("uploading");
        return ResultBean.success();
    }
    
    @PostMapping("/createEntity")
    public ResultBean createEntity(
            @RequestBody String headId, @RequestBody String relationId, @RequestBody String tailId,
            @RequestBody String name,
            @RequestBody String comment,
            @RequestBody String nameEn,
            @RequestBody String nameCn,
            @RequestBody String division,
            @RequestBody String from
    ){
        logger.log("KGController createEntity");
        //todo
        return null;
    }

    @PostMapping("/createProperty")
    public ResultBean createProperty(
            @RequestBody String name,
            @RequestBody String comment,
            @RequestBody String nameEn,
            @RequestBody String nameCn,
            @RequestBody String from,
            @RequestBody String domain,
            @RequestBody String range
    ){
        logger.log("KGController createProperty");
        //todo
        return null;
    }

    @PostMapping("/createLink")
    public ResultBean createLink(){
        return null;
    }

    @PostMapping("/updateItem")
    public ResultBean updateItem(){
        return null;
    }

    @PostMapping("/replaceItem")
    public ResultBean replaceItem(
            @RequestBody String headId, @RequestBody String relationId, @RequestBody String tailId,
            @RequestBody String itemId,
            @RequestBody String name,
            @RequestBody String comment,
            @RequestBody String nameEn,
            @RequestBody String nameCn,
            @RequestBody String division,
            @RequestBody String from
    ){
        logger.log("KGController replaceItem");
        //todo
        return null;
    }

    @PostMapping("/deleteItem")
    public ResultBean deleteItem(){
        return null;
    }

    @PostMapping("/deleteLink")
    public ResultBean deleteLink(
            @RequestBody String headId, @RequestBody String relationId, @RequestBody String tailId
    ){
        logger.log("KGController deleteLink");
        //todo
        return null;
    }

}
