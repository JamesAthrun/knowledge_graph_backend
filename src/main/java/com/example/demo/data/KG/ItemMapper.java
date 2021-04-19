package com.example.demo.data.KG;

import com.example.demo.po.ItemPo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMapper {

    void insert(ItemPo itemPo);

    List<ItemPo> searchByKeywords(String keywords);

    ItemPo getById(String id);

    void deleteById(String id);

}
