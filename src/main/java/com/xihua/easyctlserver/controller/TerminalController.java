package com.xihua.easyctlserver.controller;

import com.xihua.easyctlserver.annotations.UserAuth;
import com.xihua.easyctlserver.dao.model.Topic;
import com.xihua.easyctlserver.dao.model.TopicApi;
import com.xihua.easyctlserver.dao.model.User;
import com.xihua.easyctlserver.domain.Response;
import com.xihua.easyctlserver.domain.TerminalCmdReq;
import com.xihua.easyctlserver.domain.TopicApiRegisterReq;
import com.xihua.easyctlserver.exception.TopicApiExistsException;
import com.xihua.easyctlserver.service.TopicApiService;
import com.xihua.easyctlserver.service.TopicService;
import com.xihua.easymqtt.MClient;
import com.xihua.easymqtt.domain.Message;
import com.xihua.easymqtt.exceptions.MWaitResonseException;
import com.xihua.easymqtt.exceptions.MqttServerConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@UserAuth(paramsWithUser = true)
@RestController
@RequestMapping("/terminal/")
public class TerminalController {
    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicApiService topicApiService;

    @Value("${easymqtt.brokerHost}")
    private String brokerHost;

    @Value("${easymqtt.mqttAccount}")
    private String mqttAccount;

    @Value("${easymqtt.mqttPassword}")
    private String mqttPassword;

    private static final String persistenceDir = Objects.requireNonNull(TerminalController.class.getResource("")).getPath();

    private final Logger logger = LoggerFactory.getLogger(TerminalController.class);

    @PostMapping(value = "call")
    public Response<Message> call(User user, @RequestBody TerminalCmdReq req) {
        TopicApi tTopicApi = topicApiService.get(req.getTopicApiId());
        if (tTopicApi == null) {
            return new Response<>(false, "topicApi not exists: " + req.getTopicApiId());
        }
        Topic tTopic = topicService.getTopicByUIdTid(user.getId(), tTopicApi.getTid());
        Topic uTopic = topicService.getUserTopicByUId(user.getId());

        MClient client;
        try {
            client = new MClient(brokerHost, uTopic.getTopic(), mqttAccount, mqttPassword, persistenceDir);
        } catch (MqttServerConnectException e) {
            logger.error("init client error: ", e);
            return new Response<>(false, "init client error: " + e.getMessage());
        }
        try {
            return new Response<>(true, client.call(tTopic.getTopic(), tTopicApi.getApi(),
                    Arrays.asList(tTopicApi.getParams().split(","))));
        } catch (MWaitResonseException e) {
            logger.error("call terminal error: ", e);
            return new Response<>(false, "call terminal error: " + e.getMessage());
        }
    }
}