package com.example.demo.bl.Verify;

import com.example.demo.util.ResultBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VerifyService {

    ResultBean getDesKey(String ip, String modulus, String exponent) throws Exception;
}
