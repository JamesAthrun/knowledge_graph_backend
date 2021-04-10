package com.example.demo.data.Account;

import com.example.demo.po.AccountPo;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper {

    void register(AccountPo accountpo);

    String selectPwdByName(String name);
}
