package com.example.demo.controller.Account;

import com.example.demo.bl.Account.AccountService;
import com.example.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/Account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("/register")
    public ResultBean register(@RequestParam String name,@RequestParam String pwd){
        return accountService.register(name,pwd);
    }

    @GetMapping("/login")
    public ResultBean login(@RequestParam String name,@RequestParam String pwd){
        return accountService.login(name,pwd);
    }
}
