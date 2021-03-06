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
    }

    public synchronized Integer OpCommitItemChange(KGEditFormVo f) {
        long res = jedis.rpush("IC:" + f.user, Trans.javaObjectToJsonStr(f));
        if (res > 0L) {
            jedis.expire("IC:" + f.user, 3600L);
            return 1;
        } else return 0;
    }

    public synchronized Integer OpCancelItemChange(String userName) {
        String res = jedis.rpop("IC:" + userName);
        return res != null ? 1 : 0;
    }

    public synchronized List<String> getOpsOfUser(String userName) {
        long size = jedis.llen("IC:" + userName);
        return jedis.lrange("IC:" + userName, 0, size);
    }


    public synchronized Integer OpConfirmChange(String userName) {
        long res = jedis.del("IC:" + userName);
        return res == 1L ? 1 : 0;
    }
}
