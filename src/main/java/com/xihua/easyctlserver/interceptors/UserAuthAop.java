package com.xihua.easyctlserver.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.xihua.easyctlserver.annotations.UserAuth;
import com.xihua.easyctlserver.dao.model.User;
import com.xihua.easyctlserver.exception.UserAuthAopException;
import com.xihua.easyctlserver.service.UserService;
import com.xihua.easyctlserver.utils.JWTUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 处理参数自动填充
 */
@Aspect
@Component
public class UserAuthAop {
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    UserService userService;

    @Pointcut("execution(* com.xihua.easyctlserver.controller.*Controller.*(..))")
    public void pointcut(){}

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        User user = null;

        UserAuth annotation = method.getAnnotation(UserAuth.class);
        if (annotation == null) {
            annotation = method.getDeclaringClass().getAnnotation(UserAuth.class);
        }
        if (annotation != null) {
            user = verify(request);
        }
        if (annotation != null && annotation.paramsWithUser()) {
            args[0] = user;
        }

        return joinPoint.proceed(args);
    }

    public User verify(HttpServletRequest request) {
        String token = null;
        if (request.getCookies() == null) {
            throw new UserAuthAopException("user not login.");
        }
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("TOKEN")) {
                token = c.getValue();
                break;
            }
        }
        if (StringUtils.isBlank(token)) {
            throw new UserAuthAopException("user not login.");
        }

        try {
            JWTUtil.verify(token, secret);
        } catch (Throwable e) {
            throw new UserAuthAopException(e);
        }

        Long uid;
        try {
            Map<String, Claim> tokenInfo = JWTUtil.getTokenInfo(token, secret);
            uid = Long.valueOf(tokenInfo.get("uid").asString());
        } catch (Throwable e) {
            throw new UserAuthAopException(e);
        }

        User user = userService.get(uid);
        if (user == null) {
            throw new UserAuthAopException("user " + uid + " not exists.");
        }
        return user;
    }
}
