package com.example.demo.controller.KG;

import com.example.demo.bl.KG.KGService;
import com.example.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/KG")
public class KGController {
    @Autowired
    KGService kgService;

    @GetMapping("/search")
    public ResultBean search(@RequestParam String keywords){ return kgService.searchEntity(keywords); }

    @GetMapping("/getGraphData")
    public ResultBean getGraphData(@RequestParam String id){ return kgService.getGraphData(id); }
}
