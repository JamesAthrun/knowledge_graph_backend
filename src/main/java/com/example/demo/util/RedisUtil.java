package com.example.demo.util;

import com.example.demo.vo.KGEditFormVo;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Map;
import java.util.Set;

@Component
public class RedisUtil {
    Jedis jedis;
    static SetParams MICParams;

    public RedisUtil(){
        jedis = new Jedis("localhost",6379);
        MICParams = new SetParams().px(3600).nx();
    }

    public Integer OpCommitItemChange(KGEditFormVo f){
        long res = jedis.hset(f.user,GlobalTrans.javaObjectToJsonStr(f),"");
        if(res==1L){
            jedis.expire(f.user,3600L);
            return 1;
        }
        else return 0;

    }

    public Integer OpCancelItemChange(KGEditFormVo f){
        long res = jedis.hdel(f.user);
        return res==1L?1:0;
    }

    public Set<String> getOpsOfUser(String userName){
        Map<String,String> map = jedis.hgetAll(userName);
        return map.keySet();
    }


}
