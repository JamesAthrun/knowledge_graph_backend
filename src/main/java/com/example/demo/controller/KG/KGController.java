package com.example.demo.controller.KG;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
        return kgService.getTreeData(id);
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
    public ResultBean createEntity(@RequestBody String jsonStr){
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
    public ResultBean createProperty(@RequestBody String jsonStr){
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
    public ResultBean createLink(@RequestBody String jsonStr){
        logger.log("KGController createLink");
        JSONObject jo = JSON.parseObject(jsonStr);
        return kgService.createLink(
                jo.getString("headId"),
                jo.getString("relationId"),
                jo.getString("tailId"));
    }

    @PostMapping("/updateItem")
    public ResultBean updateItem(@RequestBody String jsonStr){
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
    public ResultBean replaceItem(@RequestBody String jsonStr){
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
    public ResultBean deleteItem(@RequestBody String id){
        logger.log("KGController deleteItem");
        return kgService.deleteItem(id);
    }

    @PostMapping("/deleteLink")
    public ResultBean deleteLink(@RequestBody String jsonStr){
        logger.log("KGController deleteLink");
        JSONObject jo = JSON.parseObject(jsonStr);
        return kgService.deleteLink(
                jo.getString("headId"),
                jo.getString("relationId"),
                jo.getString("tailId"));
    }

    @PostMapping("/ask")
    public ResultBean ask(@RequestBody String questionStr){
        logger.log("KGController ask");
        return kgService.ask(questionStr);
    }

}
