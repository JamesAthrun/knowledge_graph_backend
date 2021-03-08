package com.example.demo.controller.Jack;

import com.example.demo.bl.Jack.JackService;
import com.example.demo.util.ResultBean;
import com.example.demo.vo.JackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/Jack")
public class JackController {
    @Autowired
    JackService jackService;

    @PostMapping("/addJack")
    public ResultBean addJack(@RequestBody JackVo jackVo){ return jackService.addJack(jackVo); }

    @GetMapping("/delJack")
    public ResultBean delJack(@RequestParam int jackId){ return jackService.delJack(jackId); }

    @GetMapping("/getJack")
    public ResultBean getJack(@RequestParam int jackId){ return jackService.getJack(jackId); }

    @PostMapping("/updateJack")
    public ResultBean updateJack(@RequestBody JackVo jackVo){ return jackService.updateJack(jackVo); }
}
