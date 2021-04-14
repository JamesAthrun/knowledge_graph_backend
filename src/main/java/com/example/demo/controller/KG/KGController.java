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
    public ResultBean createEntity(String jsonStr){
        //todo
        return null;
    }

    @PostMapping("/createProperty")
    public ResultBean createProperty(String jsonStr){
        //todo
        return null;
    }

    @PostMapping("/createLink")
    public ResultBean createLink(String jsonStr){
        //todo
        return null;
    }

    @PostMapping("/updateItem")
    public ResultBean updateItem(String jsonStr){
        //todo
        return null;
    }

    @PostMapping("/replaceItem")
    public ResultBean replaceItem(String jsonStr){
        //todo
        return null;
    }

    @PostMapping("/deleteItem")
    public ResultBean deleteItem(String jsonStr){
        //todo
        return null;
    }

    @PostMapping("/deleteLink")
    public ResultBean deleteLink(String jsonStr){
        //todo
        return null;
    }

}
