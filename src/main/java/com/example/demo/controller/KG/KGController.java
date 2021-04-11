package com.example.demo.controller.KG;

import com.example.demo.bl.KG.KGService;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResultBean createGraphByJsonStr(@RequestParam String jsonStr){
        logger.log("KGController createGraphByJsonStr");
        return kgService.createGraphByJsonStr(jsonStr);
    }

}
