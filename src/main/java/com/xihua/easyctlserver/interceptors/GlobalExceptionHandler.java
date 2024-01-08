package com.xihua.easyctlserver.interceptors;

import com.xihua.easyctlserver.domain.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(basePackages="com.xihua.easyctlserver.controller")
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response<Void> exceptionHandler(Exception e) {
        logger.warn("exception caught: ", e);
        return new Response<>(false, e.getMessage());
    }
}