package com.example.demo.controller.Account;

import com.example.demo.anno.CryptAnno;
import com.example.demo.bl.Account.AccountService;
import com.example.demo.data.Verify.VerifyMapper;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import com.example.demo.vo.AccountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("")
@Api(
        value = "用户相关",
        tags = "用户"
)
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    VerifyMapper verifyMapper;
    @Autowired
    GlobalLogger logger;

    @CryptAnno
    @PostMapping("/login")
    @ApiOperation(
            value = "用户登录",
            notes = ""
    )
    @ApiImplicitParam(name = "s", value = "加密后的json字符串，如{\"name\":\"obama\",\"pwd\":\"123456\",\"email\":\"example@qq.com\"} -encrypt-> hexStr")
    public ResultBean login(HttpServletRequest request, @RequestBody String s) throws Exception {
        logger.log("AccountController login");
        AccountVo account = new AccountVo(s);
        ResultBean res =  accountService.login(account.name, account.pwd);
        if(res.code==1) verifyMapper.setUserName(request.getRemoteAddr(),account.name);
        return res;
    }

    @CryptAnno
    @PostMapping("/signup")
    @ApiOperation(
            value = "用户注册",
            notes = ""
    )
    @ApiImplicitParam(name = "s", value = "加密后的json字符串，如{\"name\":\"obama\",\"pwd\":\"123456\",\"email\":\"example@qq.com\"} -encrypt-> hexStr")
    public ResultBean register(HttpServletRequest request, @RequestBody String s) throws Exception {
        logger.log("AccountController signup");
        AccountVo account = new AccountVo(s);
        return accountService.register(account.name, account.pwd, account.email);
    }

    @GetMapping("/getGroupList")
    @ApiOperation(
            value = "获取特定用户用户组列表",
            notes = ""
    )
    public ResultBean getGroupList(HttpServletRequest request, @RequestParam int userId) {
        return accountService.getGroupList(userId);
    }
}
