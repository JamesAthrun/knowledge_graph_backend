package com.example.demo.data.KG;

import com.example.demo.po.QuestionPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {

    void insert(@Param("keyWords") String keyWords, @Param("help") String help, @Param("relatedIds") String relatedIds, @Param("tableId") String tableId, @Param("ver") String ver);

    List<QuestionPo> getAll(@Param("tableId") String tableId);
}