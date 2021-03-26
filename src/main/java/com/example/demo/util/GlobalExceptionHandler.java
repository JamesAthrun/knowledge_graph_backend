package com.example.demo.util;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResultBean unknownException(Exception e) {
        e.printStackTrace();
        return ResultBean.error(2,"unknownException");
    }

    @ExceptionHandler
    public ResultBean DuplicateKeyException(DuplicateKeyException e) {
        return ResultBean.error(4,"var must be unique");
    }

}
