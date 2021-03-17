package com.example.demo.data.KG;

import com.example.demo.po.TriplePo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripleMapper {

    void insert(TriplePo triplePo);

    List<TriplePo> getRelatedTriples(String id);
}
