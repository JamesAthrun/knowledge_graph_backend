package com.example.demo.blImpl.Jack;

import com.example.demo.bl.Jack.JackService;
import com.example.demo.data.Jack.JackMapper;
import com.example.demo.po.JackPo;
import com.example.demo.util.ResultBean;
import com.example.demo.vo.JackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JackServiceImpl implements JackService {

    @Autowired
    JackMapper jackMapper;

    @Override
    public ResultBean addJack(JackVo jackVo){
        JackPo jackPo = new JackPo();
        jackPo.dsp = jackVo.dsp;
        jackMapper.addJack(jackPo);
        return ResultBean.success();
    }

    @Override
    public ResultBean delJack(int jackId){
        jackMapper.delJack(jackId);
        return ResultBean.success();
    }

    @Override
    public ResultBean getJack(int jackId){
        JackVo jackVo = new JackVo();
        JackPo jackPo = jackMapper.getJack(jackId);
        jackVo.id = jackPo.id;
        jackVo.ts = (int)((Long.parseLong(String.valueOf(new Date().getTime()))/1000)%(86400));
        return ResultBean.success(jackVo);
    }

    @Override
    public ResultBean updateJack(JackVo jackVo){
        JackPo jackPo = new JackPo();
        jackPo.dsp = jackVo.dsp;
        jackMapper.updateJack(jackPo);
        return ResultBean.success();
    }
}
