package com.example.demo.bl.KG;

import com.example.demo.util.ResultBean;
import com.example.demo.vo.KGEditFormVo;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface KGService {

    ResultBean searchEntity(String keywords, String ver);

    ResultBean getGraphData(String id, String ver);

    ResultBean getTreeData(String id, String ver);

    ResultBean createGraphByJsonStr(String jsonStr, String name);

    ResultBean ask(String questionStr);

    ResultBean commitChange(KGEditFormVo f);

    ResultBean cancelChange(String userName);

    ResultBean confirmChange(String userName);

    ResultBean rollBackChange(String ver, String tableId);

    ResultBean getGraphInfo(String tableId);

    ResultBean getAllGraphInfo();

    ResultBean getGraphHistory(String tableId);

    ResultBean changeTablePermission(String tableId, int authority);
}
