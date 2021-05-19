package com.example.demo.data.KG;

import com.example.demo.po.ItemPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemMapper {

    void insert(ItemPo itemPo);

    List<ItemPo> searchByKeywords(@Param("keywords")String keywords, @Param("ver")String ver);

    ItemPo getById(@Param("id") String id, @Param("ver") String ver);

    void deleteById(String id);

}
