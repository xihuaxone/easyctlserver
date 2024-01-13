package com.xihua.easyctlserver.controller;

import com.xihua.easyctlserver.dao.model.User;
import com.xihua.easyctlserver.dao.model.UserAuth;
import com.xihua.easyctlserver.domain.LoginReq;
import com.xihua.easyctlserver.domain.RegisterReq;
import com.xihua.easyctlserver.domain.Response;
import com.xihua.easyctlserver.exception.UserAuthExistsException;
import com.xihua.easyctlserver.exception.UserExistsException;
import com.xihua.easyctlserver.service.UserAuthService;
import com.xihua.easyctlserver.service.UserService;
import com.xihua.easyctlserver.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequestMapping("/auth/")
public class LoginController {
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    UserAuthService userAuthService;

    @Autowired
    UserService userService;

    @PostMapping(value = "login")
    public Response<Void> login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginReq req) {
        UserAuth userAuth = userAuthService.get(req.getLoginAccount());
        if (userAuth == null) {
            return new Response<>(false, "user auth not exists.");
        }
        if (!userAuth.getLoginPassword().equals(req.getPassword())) {
            return new Response<>(false, "login failed.");
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("uid", String.valueOf(userAuth.getuId()));
        String token = JWTUtil.generateToken(map, secret, 24 * 7);
        response.addCookie(new Cookie("TOKEN", token));
        response.setHeader("Authorization", "Bearer " + token);
        return new Response<>(true);
    }

    @PostMapping(value = "register")
    public Response<Void> register(@RequestBody RegisterReq req) {
        try {
            userService.register(req);
        } catch (UserExistsException e) {
            return new Response<>(false, "user " + req.getNickName() + " exists.");
        } catch (UserAuthExistsException e) {
            return new Response<>(false, "user account" + req.getLoginAccount() + " exists.");
        }

        return new Response<>(true);
    }
}