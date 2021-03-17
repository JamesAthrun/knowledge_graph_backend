package com.example.demo.data.KG;

import com.example.demo.po.PropertyPO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyMapper {
    
    void insert(PropertyPO propertyPO);
    
    List<PropertyPO> searchByKeywords(String keywords);

    PropertyPO getByRecordId(String recordId);
}
