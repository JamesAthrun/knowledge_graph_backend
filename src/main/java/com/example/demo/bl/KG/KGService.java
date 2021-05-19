package com.example.demo.bl.KG;

import com.example.demo.util.ResultBean;
import com.example.demo.vo.KGEditFormVo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface KGService {

    ResultBean searchEntity(String keywords);

    ResultBean getGraphData(String id);

    ResultBean getTreeData(String id);

    ResultBean createGraphByJsonStr(String jsonStr);

//    ResultBean createItem(String headId, String relationId, String tailId, String tableId, String title, String name, String division, String comment);
//
//    ResultBean createLink(String tableId, String headId, String relationId, String tailId);
//
//    ResultBean updateItem(String id, String tableId, String title, String name, String division, String comment);
//
//    ResultBean replaceItem(String headId, String relationId, String tailId, String id, String tableId, String title, String name, String division, String comment);
//
//    ResultBean deleteItem(String id);
//
//    ResultBean deleteLink(String headId, String relationId, String tailId);

    ResultBean ask(String questionStr);

    ResultBean commitChange(KGEditFormVo f);

    ResultBean cancelChange(KGEditFormVo f);

    ResultBean confirmChange(String userName);

    ResultBean rollBackChange(String ver);
}
