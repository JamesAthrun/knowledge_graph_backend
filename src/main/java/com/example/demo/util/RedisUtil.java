package com.example.demo.util;

import com.example.demo.vo.KGEditFormVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

@Component
public class RedisUtil {
    Jedis jedis;
    @Autowired
    GlobalLogger logger;
//    static SetParams ICParams;

    public RedisUtil() {
        jedis = new Jedis("localhost", 6379);
        jedis.flushAll();
//        ICParams = new SetParams().px(3600).nx();
    }

    public Integer OpCommitItemChange(KGEditFormVo f) {
        long res = jedis.rpush("IC:" + f.user, Trans.javaObjectToJsonStr(f));
        logger.log("rpush结果: "+res);
        if (res == 1L) {
            jedis.expire("IC:" + f.user, 3600L);
            return 1;
        } else return 0;
    }

    public Integer OpCancelItemChange(KGEditFormVo f) {
        String res = jedis.lpop("IC:" + f.user);
        return res.equals("OK") ? 1 : 0;
    }

    public List<String> getOpsOfUser(String userName) {
        long size = jedis.llen("IC:" + userName);
        return jedis.lrange("IC:" + userName, 0, size);
    }


    public Integer OpConfirmChange(String userName) {
        long res = jedis.del("IC:" + userName);
        return res == 1L ? 1 : 0;
    }
}
