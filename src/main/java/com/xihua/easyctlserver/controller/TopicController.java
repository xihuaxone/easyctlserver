package com.xihua.easyctlserver.controller;

import com.xihua.easyctlserver.annotations.UserAuth;
import com.xihua.easyctlserver.dao.model.Topic;
import com.xihua.easyctlserver.dao.model.TopicApi;
import com.xihua.easyctlserver.dao.model.User;
import com.xihua.easyctlserver.domain.Response;
import com.xihua.easyctlserver.domain.TopicApiRegisterReq;
import com.xihua.easyctlserver.domain.TopicRegisterReq;
import com.xihua.easyctlserver.domain.TopicUpdateReq;
import com.xihua.easyctlserver.domain.dto.TopicApiDTO;
import com.xihua.easyctlserver.domain.dto.TopicDTO;
import com.xihua.easyctlserver.exception.TopicApiExistsException;
import com.xihua.easyctlserver.exception.TopicExistsException;
import com.xihua.easyctlserver.exception.TopicNotExistsException;
import com.xihua.easyctlserver.service.TopicApiService;
import com.xihua.easyctlserver.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping(value = "list")
    public Response<List<TopicDTO>> list(User user) {
        List<Topic> terminalTopicList = topicService.getTTopicsByUId(user.getId());
        List<TopicDTO> topicDTOList = new ArrayList<>();
        for (Topic topic : terminalTopicList) {
            TopicDTO topicDTO = new TopicDTO();
            topicDTO.setId(topic.getId());
            topicDTO.setTopic(topic.getTopic());
            topicDTO.setStat(topic.getStat());

            List<TopicApiDTO> apiDTOList = new ArrayList<>();
            List<TopicApi> apiList = topicApiService.getByTid(topic.getId());
            for (TopicApi api : apiList) {
                TopicApiDTO topicApiDTO = new TopicApiDTO();
                topicApiDTO.setId(api.getId());
                topicApiDTO.setTid(topic.getId());
                topicApiDTO.setApi(api.getApi());
                topicApiDTO.setParams(api.getParams());
                topicApiDTO.setActionName(api.getActionName());
                apiDTOList.add(topicApiDTO);
            }
            topicDTO.setTopicApiDTOList(apiDTOList);
            topicDTOList.add(topicDTO);
        }
        return new Response<>(true, topicDTOList);
    }

    @PostMapping(value = "register")
    public Response<Void> control(User user, @RequestBody TopicRegisterReq req) {
        Topic uTopic = new Topic();
        uTopic.setTopic(req.getUTopic());
        Topic tTopic = new Topic();
        tTopic.setTopic(req.getTTopic());
        try {
            topicService.add(user.getId(), uTopic, Collections.singletonList(tTopic));
        } catch (TopicExistsException e) {
            return new Response<>(false, "topic exists.");
        }
        return new Response<>(true);
    }

    @PostMapping(value = "update")
    public Response<Void> update(User user, @RequestBody TopicUpdateReq req) {
        Topic oldTopic = topicService.getTopicByUIdTopic(user.getId(), req.getTopic());
        try {
            topicService.update(oldTopic.getId(), req.getStatus());
        } catch (TopicNotExistsException e) {
            return new Response<>(false, "topic " + req.getTopic() + " not exists.");
        }
        return new Response<>(true);
    }

    @PostMapping(value = "delete")
    public Response<Void> delete(User user, @RequestBody String topic) {
        Topic oldTopic = topicService.getTopicByUIdTopic(user.getId(), topic);
        topicService.delete(oldTopic.getId());
        return new Response<>(true);
    }

    @PostMapping(value = "api/register")
    public Response<Void> topicApiRegister(User user, @RequestBody TopicApiRegisterReq req) {
        Topic topic = topicService.getTopicByUIdTopic(user.getId(), req.getTopic());
        if (topic == null) {
            return new Response<>(false, "topic " + req.getTopic() + " not exists.");
        }
        try {
            topicApiService.add(topic.getId(), req.getApi(), String.join(",", req.getParams()), req.getActionName());
        } catch (TopicApiExistsException e) {
            return new Response<>(true);
        }
        return new Response<>(true);
    }
}