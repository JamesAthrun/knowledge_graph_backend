package com.example.demo.util;

import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @Autowired
    GlobalLogger logger;

    private String getStackInfo(Exception e) {
        StackTraceElement[] arr = e.getStackTrace();
        StringBuilder sb = new StringBuilder();
        sb.append("print stack trace 5\n");
        for (int i = 0; i < 5; i++) {
            sb.append(arr[i].toString()).append("\n");
        }
        return sb.toString();
    }

    @ExceptionHandler
    public ResultBean unknownException(Exception e) {
        logger.error(getStackInfo(e));
        return ResultBean.error(2, "unknownException");
    }

    @ExceptionHandler
    public ResultBean DuplicateKeyException(DuplicateKeyException e) {
        logger.error(getStackInfo(e));
        return ResultBean.error(4, "var must be unique");
    }

    @ExceptionHandler
    public void ioException(ClientAbortException e){
        logger.log("client abort connection");
    }
}
