package com.example.demo.data.KG;

import com.example.demo.po.EntityPo;
import com.example.demo.po.NumIdMapPo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NumIdMapMapper {
    void insertNumIdMap(NumIdMapPo numIdMapPo);
    List<EntityPo> searchEntity(String keywords);
}
