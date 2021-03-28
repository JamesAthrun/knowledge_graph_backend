package com.example.demo.controller.Verify;

import com.example.demo.bl.Verify.VerifyService;
import com.example.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController()
@RequestMapping("/Verify")
public class VerifyController {
    @Autowired
    VerifyService verifyService;

    @GetMapping("/getDesKey")
    public ResultBean getDesKey(HttpServletRequest request, @RequestParam String modulus, @RequestParam String exponent) throws Exception {
        //获取用户ip
        String ip = request.getRemoteAddr();
        //获取cookies
        String cookies = Arrays.toString(request.getCookies());
        return verifyService.getDesKey(ip,modulus,exponent);
    }
}
