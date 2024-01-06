package com.xihua.easyctlserver.controller;

import com.xihua.easyctlserver.domain.LoginReq;
import com.xihua.easyctlserver.domain.Response;
import com.xihua.easymqtt.MClient;
import com.xihua.easymqtt.exceptions.MqttServerConnectException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Value("easymqtt.brokerHost")
    private String brokerHost;

    private static final String persistenceDir = Objects.requireNonNull(LoginController.class.getResource("")).getPath();

    @GetMapping(value = "login")
    public String login(@RequestParam(name = "loginReq", required = false) LoginReq req) {
        MClient client = null;
        try {
            client = new MClient(brokerHost, req.getTopic(), req.getUsername(), req.getPassword(), persistenceDir);
        } catch (MqttServerConnectException e) {
            return "error";
        }
        return "terminalControl";
    }
}