package com.example.demo.data.KG;

import com.example.demo.po.EntityPo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityMapper {

    void insert(EntityPo entityPo);

    List<EntityPo> searchByKeywords(String keywords);

    EntityPo getByRecordId(String recordId);

    void deleteById(String id);

}
