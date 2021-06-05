package com.example.demo.blImpl.AccountServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.bl.Account.GroupService;
import com.example.demo.data.Account.GroupMapper;
import com.example.demo.data.Account.UserGroupMapper;
import com.example.demo.po.GroupPo;
import com.example.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    UserGroupMapper userGroupMapper;

    @Override
    public ResultBean addGroup(String jsonString) {
        JSONObject jojo = JSONObject.parseObject(jsonString);
        String name = jojo.getString("name");
        String description = jojo.getString("description");
        groupMapper.addGroup(new GroupPo(name, description));
        return ResultBean.success();
    }

    @Override
    public ResultBean getUserList(int groupId) {
        return ResultBean.success(userGroupMapper.selectGroupsByGroupId(groupId));
    }
}
