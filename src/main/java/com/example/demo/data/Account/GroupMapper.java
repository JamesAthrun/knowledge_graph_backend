package com.example.demo.data.Account;

import com.example.demo.po.GroupPo;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMapper {

    void addGroup(GroupPo groupPo);

    GroupPo selectGroupById(int id);

    GroupPo selectGroupByName(String name);
}
