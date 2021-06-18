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
        return doJudge(tableId, userId, 2);
    }

    public boolean getReadPermission(String tableId, int userId) {
        return doJudge(tableId, userId, 1);
    }

    public boolean doJudge(String tableId, int userId, int levelNeed) {
        GraphPo go = graphMapper.get(tableId);
        int authCode = go.authority;
        if (authCode % 100 % 10 >= levelNeed) return true;
        else if (userId == go.userId && authCode / 100 >= levelNeed) return true;
        else if ((authCode - authCode / 100) % 10 >= levelNeed) {
            List<GroupPo> userInGroups = userGroupMapper.selectGroupsByUserId(userId);
            for (GroupPo userInGroup : userInGroups) {
                if (userInGroup.groupId == go.groupId) return true;
            }
        }
        return false;
    }
}
