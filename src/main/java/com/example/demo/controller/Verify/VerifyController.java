package com.example.demo.controller.Verify;

import com.example.demo.bl.Verify.VerifyService;
import com.example.demo.util.GlobalLogger;
import com.example.demo.util.ResultBean;
import com.example.demo.vo.EncryptionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/Verify")
@Api(
        value = "加密相关",
        tags = "加密"
)
public class VerifyController {
    @Autowired
    VerifyService verifyService;
    @Autowired
    GlobalLogger logger;

    @PostMapping("/getDesKey")
    @ApiOperation(
            value = "接受RSA公钥，返回经RSA加密后的DES密钥",
            notes = ""
    )
    public ResultBean getDesKey(HttpServletRequest request, @RequestBody EncryptionVo encryptionVO) throws Exception {
        logger.log("VerifyController getDesKey");
        //获取用户ip
        String ip = request.getRemoteAddr();
        //获取cookies
//        String cookies = Arrays.toString(request.getCookies());
        return verifyService.getDesKey(ip, encryptionVO.modulus, encryptionVO.exponent);
    }
}