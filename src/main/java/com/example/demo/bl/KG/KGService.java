package com.example.demo.bl.KG;

import com.example.demo.util.ResultBean;

public interface KGService {

    ResultBean searchEntity(String keywords);
    ResultBean getGraphData(String id);
    ResultBean createGraphByJsonStr(String jsonStr);

}
