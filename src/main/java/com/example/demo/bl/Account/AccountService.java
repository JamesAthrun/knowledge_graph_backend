package com.example.demo.bl.Account;

import com.example.demo.util.ResultBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AccountService {

    ResultBean login(String name, String pwd);

    ResultBean register(String name, String pwd, String email);

    ResultBean getGroupList(int userId);
}
