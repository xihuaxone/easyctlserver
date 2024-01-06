package com.xihua.easyctlserver.controller;

import com.alibaba.fastjson.JSON;
import com.xihua.easyctlserver.domain.LoginReq;
import com.xihua.easyctlserver.domain.Response;
import com.xihua.easyctlserver.domain.TerminalControlReq;
import com.xihua.easymqtt.MClient;
import com.xihua.easymqtt.exceptions.MqttServerConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/terminal")
public class TerminalController {

    private final Logger logger = LoggerFactory.getLogger(TerminalController.class);

    @Value("easymqtt.brokerHost")
    private String brokerHost;

    private static final String persistenceDir = Objects.requireNonNull(TerminalController.class.getResource("")).getPath();

    @PostMapping(value = "control")
    public Response.ResponseBuilder<Void> control(@RequestParam TerminalControlReq req) {
        logger.info(JSON.toJSONString(req));
        return Response.<Void>builder().success(true);
    }
}