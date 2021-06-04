package com.example.demo.bl.KG;

import com.example.demo.util.ResultBean;
import com.example.demo.vo.KGEditFormVo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface KGService {

    ResultBean searchEntity(String keywords, String ver);

    ResultBean getGraphData(String id, String ver);

    ResultBean getTreeData(String id, String ver);

    ResultBean createGraphByJsonStr(String jsonStr);

    ResultBean ask(String questionStr);

    ResultBean commitChange(KGEditFormVo f);

    ResultBean cancelChange(KGEditFormVo f);

    ResultBean confirmChange(String userName);

    ResultBean rollBackChange(String ver, String tableId);

    ResultBean getGraphInfo(String tableId);

    ResultBean getAllGraphInfo();

    boolean getWritePermission(String tableId, int userId);

    boolean getReadPermission(String tableId, int userId);

    ResultBean changeTablePermission(String tableId, int authority);
}
