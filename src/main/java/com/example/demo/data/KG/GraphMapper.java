package com.example.demo.data.KG;

import com.example.demo.po.GraphPo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphMapper {

    void insert(GraphPo graphPo);

    List<GraphPo> get(String tableId);

    Integer getSize();
}
