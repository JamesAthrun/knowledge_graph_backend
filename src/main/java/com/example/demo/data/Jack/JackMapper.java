package com.example.demo.data.Jack;

import com.example.demo.po.JackPo;
import org.springframework.stereotype.Repository;

@Repository
public interface JackMapper {

    void addJack(JackPo jackPo);
    void delJack(int jackId);
    JackPo getJack(int jackId);
    void updateJack(JackPo jackPo);
}
