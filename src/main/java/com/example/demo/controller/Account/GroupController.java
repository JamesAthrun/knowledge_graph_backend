package com.example.demo.controller.Account;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bl.Account.GroupService;
import com.example.demo.po.GroupPo;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("")
@Api(
        value = "用户组相关",
        tags = "用户"
)
public class GroupController {
    @Autowired
    GroupService groupService;
    @Autowired
    GlobalLogger logger;

    @PostMapping("/addGroup")
    @ApiOperation(
            value = "创建用户组",
            notes = ""
    )
    public ResultBean addGroup(@RequestBody String jsonString){
        return groupService.addGroup(jsonString);
    }

    @GetMapping("/getUserList")
    @ApiOperation(
            value = "获取特定用户组用户列表",
            notes = ""
    )
    public ResultBean getUserList(@RequestParam int groupId) {
        return groupService.getUserList(groupId);
    }

}
