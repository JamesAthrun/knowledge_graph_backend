package com.example.demo.data.Verify;

import org.springframework.stereotype.Repository;

@Repository
public interface VerifyMapper {

    void insert(String ip, String desKey);

    String getDesKey(String ip);

}
