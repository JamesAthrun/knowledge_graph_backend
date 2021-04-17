package com.example.demo.data.KG;

import com.example.demo.po.QuestionPo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionMapper {

    List<QuestionPo> getAll();

//    QuestionPo get(String id);
}