package com.example.demo.data.KG;

import com.example.demo.po.PropertyPo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyMapper {

    void insert(PropertyPo propertyPO);

    List<PropertyPo> searchByKeywords(String keywords);

    PropertyPo getByRecordId(String recordId);

    void deleteById(String id);

    void updateItem(String id, String comment, String nameEn, String nameCn, String division, String from, String domain, String range);
}
