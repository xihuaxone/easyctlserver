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
            return Response.<Void>builder().success(false).errMsg("user auth not exists.").build();
        }
        if (!userAuth.getLoginPassword().equals(req.getPassword())) {
            return Response.<Void>builder().success(false).errMsg("login failed.").build();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("uid", String.valueOf(userAuth.getuId()));
        String token = JWTUtil.generateToken(map, secret, 24 * 7);
        response.addCookie(new Cookie("TOKEN", token));
        return Response.<Void>builder().success(true).build();
    }

    @PostMapping(value = "register")
    public Response<Void> register(@RequestBody RegisterReq req) {
        User user;
        try {
            user = userService.add(req.getNickName());
        } catch (UserExistsException e) {
            return Response.<Void>builder().success(false).errMsg("user " + req.getNickName() + " exists.").build();
        }

        try {
            userAuthService.add(user.getId(), req.getLoginAccount(), req.getPassword());
        } catch (UserAuthExistsException e) {
            return Response.<Void>builder().success(false).errMsg("user account" + req.getLoginAccount() + " exists.").build();
        }
        return Response.<Void>builder().success(true).build();
    }
}