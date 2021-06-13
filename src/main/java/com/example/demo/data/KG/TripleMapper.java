package com.example.demo.data.KG;

import com.example.demo.po.TriplePo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripleMapper {

    void insert(TriplePo triplePo);

    List<TriplePo> getRelatedTriples(@Param("id") String id, @Param("ver") String ver);

    Integer getListSize();

    void delete(String headId, String relationId, String tailId);
}
