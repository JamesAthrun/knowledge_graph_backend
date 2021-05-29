package com.example.demo.data.KG;

import com.example.demo.po.GraphPo;
import com.example.demo.po.HistoryPo;
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

    List<HistoryPo> getHistory(@Param("tableId") String tableId);

    void createHistory(@Param("tableId") String tableId, @Param("ver") String ver, @Param("time") String time, @Param("detail") String detail);
}
