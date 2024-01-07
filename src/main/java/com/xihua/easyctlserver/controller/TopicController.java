package com.xihua.easyctlserver.controller;

import com.xihua.easyctlserver.annotations.UserAuth;
import com.xihua.easyctlserver.dao.model.Topic;
import com.xihua.easyctlserver.dao.model.User;
import com.xihua.easyctlserver.domain.Response;
import com.xihua.easyctlserver.domain.TopicApiRegisterReq;
import com.xihua.easyctlserver.domain.TopicRegisterReq;
import com.xihua.easyctlserver.domain.TopicUpdateReq;
import com.xihua.easyctlserver.exception.TopicApiExistsException;
import com.xihua.easyctlserver.exception.TopicExistsException;
import com.xihua.easyctlserver.exception.TopicNotExistsException;
import com.xihua.easyctlserver.service.TopicApiService;
import com.xihua.easyctlserver.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@UserAuth(paramsWithUser = true)
@RestController
@RequestMapping("/topic/")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicApiService topicApiService;

    private final Logger logger = LoggerFactory.getLogger(TopicController.class);

    @PostMapping(value = "list")
    public Response.ResponseBuilder<List<Topic>> list(User user) {
        List<Topic> terminalTopicList = topicService.getTTopicsByUId(user.getId());
        return Response.<List<Topic>>builder().success(true).data(terminalTopicList);
    }

    @PostMapping(value = "register")
    public Response.ResponseBuilder<Void> control(User user, @RequestBody TopicRegisterReq req) {
        Topic uTopic = new Topic();
        uTopic.setTopic(req.getUTopic());
        Topic tTopic = new Topic();
        tTopic.setTopic(req.getTTopic());
        try {
            topicService.add(user.getId(), uTopic, Collections.singletonList(tTopic));
        } catch (TopicExistsException e) {
            return Response.<Void>builder().success(false).errMsg("topic exists.");
        }
        return Response.<Void>builder().success(true);
    }

    @PostMapping(value = "update")
    public Response.ResponseBuilder<Void> update(User user, @RequestBody TopicUpdateReq req) {
        Topic oldTopic = topicService.getTopicByUIdTopic(user.getId(), req.getTopic());
        try {
            topicService.update(oldTopic.getId(), req.getStatus());
        } catch (TopicNotExistsException e) {
            return Response.<Void>builder().success(false).errMsg("topic " + req.getTopic() + " not exists.");
        }
        return Response.<Void>builder().success(true);
    }

    @PostMapping(value = "delete")
    public Response.ResponseBuilder<Void> delete(User user, @RequestBody String topic) {
        Topic oldTopic = topicService.getTopicByUIdTopic(user.getId(), topic);
        topicService.delete(oldTopic.getId());
        return Response.<Void>builder().success(true);
    }

    @PostMapping(value = "api/register")
    public Response.ResponseBuilder<Void> topicApiRegister(User user, @RequestBody TopicApiRegisterReq req) {
        Topic topic = topicService.getTopicByUIdTopic(user.getId(), req.getTopic());
        if (topic == null) {
            return Response.<Void>builder().success(false).errMsg("topic " + req.getTopic() + " not exists.");
        }
        try {
            topicApiService.add(topic.getId(), req.getApi(), String.join(",", req.getParams()));
        } catch (TopicApiExistsException e) {
            return Response.<Void>builder().success(true);
        }
        return Response.<Void>builder().success(true);
    }
}