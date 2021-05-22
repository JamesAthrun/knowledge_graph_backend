package com.example.demo.data.Account;

import com.example.demo.po.AccountPo;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper {

    void register(AccountPo accountpo);

    AccountPo selectAccountByName(String name);
}
