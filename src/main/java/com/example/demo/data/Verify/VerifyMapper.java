package com.example.demo.data.Verify;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyMapper {

    void insert(String ip, String desKey);

    String getDesKey(String ip);

    void setUserName(@Param("ip") String ip,@Param("userName") String userName);

    String getUserNameByIp(@Param("ip")String ip);
}
