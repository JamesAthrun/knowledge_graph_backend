package com.example.demo.util;

import com.example.demo.vo.KGEditFormVo;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.Set;

@Component
public class RedisUtil {
    Jedis jedis;
//    static SetParams ICParams;

    public RedisUtil(){
        jedis = new Jedis("localhost",6379);
        jedis.flushAll();
//        ICParams = new SetParams().px(3600).nx();
    }

    public Integer OpCommitItemChange(KGEditFormVo f){
        long res = jedis.hset("IC:"+f.user,GlobalTrans.javaObjectToJsonStr(f),"");
        if(res==1L){
            jedis.expire(f.user,3600L);
            return 1;
        }
        else return 0;

    }

    public Integer OpCancelItemChange(KGEditFormVo f){
        long res = jedis.hdel("IC:"+f.user,GlobalTrans.javaObjectToJsonStr(f));
        return res==1L?1:0;
    }

    public Set<String> getOpsOfUser(String userName){
        Map<String,String> map = jedis.hgetAll("IC:"+userName);
        return map.keySet();
    }


}
