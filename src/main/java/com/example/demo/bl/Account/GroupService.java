package com.example.demo.bl.Account;

import com.example.demo.util.ResultBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GroupService {

    ResultBean addGroup(String jsonString);

    ResultBean getUserList(int groupId);

}
