package com.xihua.easyctlserver.controller;

import com.xihua.easyctlserver.annotations.UserAuth;
import com.xihua.easyctlserver.dao.model.Topic;
import com.xihua.easyctlserver.dao.model.TopicApi;
import com.xihua.easyctlserver.dao.model.User;
import com.xihua.easyctlserver.domain.*;
import com.xihua.easyctlserver.domain.dto.TopicApiDTO;
import com.xihua.easyctlserver.domain.dto.TopicDTO;
import com.xihua.easyctlserver.exception.TopicApiExistsException;
import com.xihua.easyctlserver.exception.TopicExistsException;
import com.xihua.easyctlserver.exception.TopicNotExistsException;
import com.xihua.easyctlserver.exception.UserTopicRelationExistsException;
import com.xihua.easyctlserver.service.TopicApiService;
import com.xihua.easyctlserver.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public Response<Void> topicApiRegister(User user, @RequestBody TopicApiRegisterReq req) {
        try {
            topicService.register(user, Collections.singletonList(req));
        } catch (TopicExistsException e) {
            return new Response<>(false, "topic " + req.getTopic() + " already exists.");
        } catch (TopicApiExistsException e) {
            return new Response<>(false, "topic api " + req.getApi() + " already exists.");
        } catch (UserTopicRelationExistsException e) {
            return new Response<>(false, "topic relation already exists.");
        }
        return new Response<>(true);
    }

    @PostMapping(value = "applyExistsTopic")
    public Response<Void> applyExistsTopic(User user, @RequestBody TopicShareReq req) {
        try {
            topicService.applyExistsTopic(user, Collections.singletonList(req));
        } catch (TopicExistsException e) {
            return new Response<>(false, "topic " + req.getTopic() + " already exists.");
        } catch (UserTopicRelationExistsException e) {
            return new Response<>(false, "topic relation already exists.");
        } catch (TopicNotExistsException e) {
            return new Response<>(false, "topic " + req.getTopic() + " not exists.");
        }
        return new Response<>(true);
    }

    @PostMapping(value = "addAction")
    public Response<Boolean> addTopicApi(User user, @RequestBody TopicApiRegisterReq req) {
        List<Topic> ownedTTopics = topicService.getTTopicsByUId(user.getId());
        Optional<Topic> owned = ownedTTopics.stream().filter(t -> t.getTopic().equals(req.getTopic())).findAny();
        if (! checkPermission(user, req.getTopic())) {
            return new Response<>(false, "user not own this topic.");
        }
        try {
            topicApiService.add(owned.get().getId(), req.getApi(), req.getParams(), req.getActionName());
        } catch (TopicApiExistsException e) {
            return new Response<>(false, "action already exists.");
        }
        return new Response<>(true);
    }

    @PostMapping(value = "update")
    public Response<Void> update(User user, @RequestBody TopicUpdateReq req) {
        TopicApi topicApi = topicApiService.get(req.getTopicApiId());
        Topic topic = topicService.get(topicApi.getTid());
        if (! checkPermission(user, topic.getTopic())) {
            return new Response<>(false, "user not own this topic.");
        }
        topicService.update(topic.getId(), req);
        return new Response<>(true);
    }

    @PostMapping(value = "delete")
    public Response<Void> delete(User user, @RequestBody String topic) {
        Topic oldTopic = topicService.getTopicByUIdTopic(user.getId(), topic);
        topicService.delete(oldTopic.getId());
        return new Response<>(true);
    }

    private boolean checkPermission(User user, String topic) {
        List<Topic> ownedTTopics = topicService.getTTopicsByUId(user.getId());
        Optional<Topic> owned = ownedTTopics.stream().filter(t -> t.getTopic().equals(topic)).findAny();
        return owned.isPresent();
    }
}