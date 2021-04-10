package com.example.demo.controller.Verify;

import com.example.demo.bl.Verify.VerifyService;
import com.example.demo.util.ResultBean;
import com.example.demo.vo.EncryptionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RestController()
@RequestMapping("/Verify")
public class VerifyController {
    @Autowired
    VerifyService verifyService;

    @PostMapping("/getDesKey")
    public ResultBean getDesKey(HttpServletRequest request, @RequestBody EncryptionVO encryptionVO) throws Exception {
        //获取用户ip
        String ip = request.getRemoteAddr();
        //获取cookies
        String cookies = Arrays.toString(request.getCookies());
        return verifyService.getDesKey(ip,encryptionVO.modulus,encryptionVO.exponent);
    }
}