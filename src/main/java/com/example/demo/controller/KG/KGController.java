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
        return kgService.createEntity(headId, relationId, tailId, name, comment, nameEn, nameCn, division, from);
    }

    @PostMapping("/createProperty")
    public ResultBean createProperty(
            @RequestBody String id,
            @RequestBody String comment,
            @RequestBody String nameEn,
            @RequestBody String nameCn,
            @RequestBody String from,
            @RequestBody String domain,
            @RequestBody String range
    ){
        logger.log("KGController createProperty");
        return kgService.createProperty(id, comment, nameEn,nameCn, from, domain, range);
    }

    @PostMapping("/createLink")
    public ResultBean createLink(
            @RequestBody String headId,
            @RequestBody String relationId,
            @RequestBody String tailId
    ){
        logger.log("KGController createLink");
        return kgService.createLink(headId, relationId, tailId);
    }

    @PostMapping("/updateItem")
    public ResultBean updateItem(
            @RequestBody String id,
            @RequestBody String comment,
            @RequestBody String nameEn,
            @RequestBody String nameCn,
            @RequestBody String division,
            @RequestBody String from,
            @RequestBody String domain,
            @RequestBody String range
    ){
        logger.log("KGController updateItem");
        return kgService.updateItem(id, comment, nameEn, nameCn, division, from, domain, range);
    }

    @PostMapping("/replaceItem")
    public ResultBean replaceItem(
            @RequestBody String id,
            @RequestBody String headId,
            @RequestBody String relationId,
            @RequestBody String tailId,
            @RequestBody String name,
            @RequestBody String comment,
            @RequestBody String nameEn,
            @RequestBody String nameCn,
            @RequestBody String division,
            @RequestBody String from,
            @RequestBody String domain,
            @RequestBody String range
    ){
        logger.log("KGController replaceItem");
        return kgService.replaceItem(id, headId, relationId, tailId, name, comment, nameEn, nameCn, division, from, domain, range);
    }

    @PostMapping("/deleteItem")
    public ResultBean deleteItem(
            @RequestBody String id
    ){
        return kgService.deleteItem(id);
    }

    @PostMapping("/deleteLink")
    public ResultBean deleteLink(
            @RequestBody String headId, @RequestBody String relationId, @RequestBody String tailId
    ){
        logger.log("KGController deleteLink");
        return kgService.deleteLink(headId,relationId,tailId);
    }

}
