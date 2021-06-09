package com.example.demo.util;

import com.example.demo.data.Account.UserGroupMapper;
import com.example.demo.data.KG.GraphMapper;
import com.example.demo.po.GraphPo;
import com.example.demo.po.GroupPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionUtil {
    @Autowired
    GraphMapper graphMapper;
    @Autowired
    UserGroupMapper userGroupMapper;

    public boolean getWritePermission(String tableId, int userId) {
        GraphPo go = graphMapper.get(tableId);
        if(go.userId == userId && go.authority / 100 == 2) return true;
        if(go.userId == userId && (go.authority / 10) % 10 == 2) {
            List<GroupPo> groupPoList = userGroupMapper.selectGroupsByUserId(userId);
            for(GroupPo groupPo: groupPoList) {
                if (groupPo.groupId == go.groupId) return true;
            }
        }
        return go.authority % 10 == 2;
    }

    public boolean getReadPermission(String tableId, int userId) {
        GraphPo go = graphMapper.get(tableId);
        if(go.userId == userId && go.authority / 100 >= 1) return true;
        if(go.userId == userId && (go.authority / 10) % 10 >= 1) {
            List<GroupPo> groupPoList = userGroupMapper.selectGroupsByUserId(userId);
            for(GroupPo groupPo: groupPoList) {
                if (groupPo.groupId == go.groupId) return true;
            }
        }
        return go.authority % 10 >= 1;
    }
}
