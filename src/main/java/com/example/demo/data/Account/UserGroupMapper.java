package com.example.demo.data.Account;

import com.example.demo.po.UserGroupPo;
import com.example.demo.po.AccountPo;
import com.example.demo.po.GroupPo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupMapper {

    void addToGroup(int userId, int groupId);

    List<GroupPo> selectGroupsByUserId(int userId);

    List<AccountPo> selectGroupsByGroupId(int groupId);
}
