package com.example.demo.data.KG;

import com.example.demo.po.TriplePo;
import org.springframework.stereotype.Repository;

@Repository
public interface TripleMapper {
    void insertTriple(TriplePo triplePo);
}
