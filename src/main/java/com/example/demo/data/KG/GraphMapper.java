package com.example.demo.data.KG;

import com.example.demo.po.GraphPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphMapper {

    void insert(GraphPo graphPo);

    GraphPo get(String tableId);

    List<GraphPo> getAll();

    String getPresentVer(@Param("tableId") String tableId);

    void confirmChange(@Param("ver") String ver, @Param("tableId") String tableId);

    void rollBack(@Param("ver") String ver, @Param("tableId") String tableId);

    void updateAuthority(@Param("tableId") String tableId, @Param("authority") int authority);
}
