package com.example.demo.bl.Jack;

import com.example.demo.util.ResultBean;
import com.example.demo.vo.JackVo;

public interface JackService {
    ResultBean addJack(JackVo jackVo);
    ResultBean delJack(int jackId);
    ResultBean getJack(int jackId);
    ResultBean updateJack(JackVo jackVo);
}
