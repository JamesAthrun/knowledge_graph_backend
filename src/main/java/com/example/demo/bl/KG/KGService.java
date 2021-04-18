package com.example.demo.bl.KG;

import com.example.demo.util.ResultBean;

public interface KGService {

    ResultBean searchEntity(String keywords);
    ResultBean getGraphData(String id);
    ResultBean getTreeData(String id);
    ResultBean createGraphByJsonStr(String jsonStr);
    ResultBean createEntity(String headId, String relationId,String tailId, String name, String comment, String nameEn, String nameCn, String division, String from);
    ResultBean createProperty(String id, String comment, String nameEn,String nameCn, String from, String domain, String range);
    ResultBean createLink(String headId, String relationId, String tailId);
    ResultBean updateItem(String id, String comment, String nameEn, String nameCn, String division, String from, String domain, String range);
    ResultBean replaceItem(String id, String headId, String relationId, String tailId, String name, String comment, String nameEn, String nameCn, String division, String from, String domain, String range);
    ResultBean deleteItem(String id);
    ResultBean deleteLink(String headId, String relationId, String tailId);
    ResultBean ask(String questionStr);
}
