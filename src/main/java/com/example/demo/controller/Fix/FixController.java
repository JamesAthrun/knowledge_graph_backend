package com.example.demo.controller.Fix;

import com.example.demo.util.GlobalLogger;
import com.example.demo.util.Recorder;
import com.example.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("")
public class FixController{
    @Autowired
    Recorder recorder;
    @Autowired
    GlobalLogger logger;

    @GetMapping("/saveRecorder")
    public ResultBean saveRecorder(@RequestParam String key){
        if(key.equals("wrng")){
            recorder.save();
            logger.log("save recorder");
        }
        return ResultBean.success();
    }

    @GetMapping("/loadRecorder")
    public ResultBean loadRecorder(@RequestParam String key){
        if(key.equals("wrng")) {
            recorder.load();
            logger.log("load recorder");
        }
        return ResultBean.success();
    }

}
