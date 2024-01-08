package com.xihua.easyctlserver.interceptors;

import com.alibaba.fastjson.JSON;
import com.xihua.easyctlserver.service.UserService;
import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.ResponseFacade;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理参数自动填充
 */
@Aspect
@Component
public class ReqLogAop {
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    UserService userService;

    private final Logger logger = LoggerFactory.getLogger(ReqLogAop.class);

    @Pointcut("execution(* com.xihua.easyctlserver.controller.*Controller.*(..))")
    public void pointcut(){}

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        StringBuilder stringBuilder = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof RequestFacade || arg instanceof ResponseFacade) {
                continue;
            }
            stringBuilder.append(JSON.toJSONString(arg));
            if (i < args.length-1) {
                stringBuilder.append(", ");
            }
        }
        logger.info("request [{}] url={}, params={}", request.getMethod(), request.getRequestURL(), stringBuilder);
    }
}
