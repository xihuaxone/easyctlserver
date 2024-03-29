package com.xihua.easyctlserver.service;

import com.alibaba.fastjson.JSON;
import com.xihua.easyctlserver.dao.mapper.TopicMapper;
import com.xihua.easyctlserver.dao.model.*;
import com.xihua.easyctlserver.domain.TopicApiRegisterReq;
import com.xihua.easyctlserver.domain.TopicShareReq;
import com.xihua.easyctlserver.domain.TopicUpdateReq;
import com.xihua.easyctlserver.enums.TopicStatEnum;
import com.xihua.easyctlserver.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private TopicApiService topicApiService;

    @Autowired
    private TopicRelationService topicRelationService;

    public Topic get(Long tid) {
        return topicMapper.selectByPrimaryKey(tid);
    }

    public Topic getUserTopicByUId(Long uid) {
        UserTopicRelationExample relationExample = new UserTopicRelationExample();
        relationExample.createCriteria().andUIdEqualTo(uid);
        List<UserTopicRelation> relations = topicRelationService.get(uid);
        for (UserTopicRelation relation : relations) {
            return get(relation.getuTid());
        }
        return null;
    }

    public List<Topic> getTTopicsByUId(Long uid) {
        List<UserTopicRelation> relations = topicRelationService.get(uid);
        return relations.stream().map(r -> get(r.gettTid())).collect(Collectors.toList());
    }

    private Topic getByTopic(String topic) {
        TopicExample example = new TopicExample();
        example.createCriteria().andTopicEqualTo(topic);
        List<Topic> topics = topicMapper.selectByExample(example);
        if (topics.isEmpty()) {
            return null;
        }
        return topics.get(0);
    }

    public Topic getTopicByUIdTopic(Long uid, String topic) {
        Topic topicInfo = getByTopic(topic);
        if (topicInfo == null) {
            return null;
        }
        List<UserTopicRelation> relations = topicRelationService.getByUidTTid(uid, topicInfo.getId());
        List<Topic> collect = relations.stream().map(r -> get(r.gettTid())).collect(Collectors.toList());
        return collect.isEmpty() ? null : collect.get(0);
    }

    public Topic getTopicByUIdTid(Long uid, Long tId) {
        List<UserTopicRelation> relations = topicRelationService.getByUidTTid(uid, tId);
        List<Topic> collect = relations.stream().map(r -> get(r.gettTid())).collect(Collectors.toList());
        return collect.isEmpty() ? null : collect.get(0);
    }

    private void addTopic(String topic, int status) throws TopicExistsException {
        if (getByTopic(topic) != null) {
            throw new TopicExistsException("topic " + topic + " already exists.");
        }
        Topic record = new Topic();
        record.setTopic(topic);
        record.setStat(status);
        topicMapper.insert(record);
    }

    public void add(Long uid, Topic uTopic, List<Topic> tTopicList) throws TopicExistsException, UserTopicRelationExistsException {
        addTopic(uTopic.getTopic(), uTopic.getStat());
        for (Topic topic : tTopicList) {
            addTopic(topic.getTopic(), topic.getStat());
        }

        for (Topic tTopic : tTopicList) {
            if (topicRelationService.getByUidUTidTTid(uid, uTopic.getId(), tTopic.getId()) != null) {
                return;
            }
            topicRelationService.add(uid, uTopic.getId(), tTopic.getId());
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public void update(Long tid, TopicUpdateReq updateReq) {
        if (updateReq.getStatus() != 0) {
            updateStatus(tid, TopicStatEnum.valueOf(updateReq.getStatus()));
        }
        topicApiService.update(updateReq.getTopicApiId(), updateReq.getApi(), updateReq.getParams());
    }

    public void updateStatus(Long tid, TopicStatEnum status) {
        if (status == null) {
            throw new RuntimeException("status illegal.");
        }
        Topic newTopic = new Topic();
        newTopic.setId(tid);
        newTopic.setStat(status.getCode());
        topicMapper.updateByPrimaryKeySelective(newTopic);
    }

    public boolean delete(Long tid) {
        return topicMapper.deleteByPrimaryKey(tid) > 0;
    }

    public boolean deleteAll(Long uid) {
        // 查询relation，获取所有topic
        List<UserTopicRelation> relations = topicRelationService.get(uid);
        for (UserTopicRelation relation : relations) {
            delete(relation.getuTid());
            delete(relation.gettTid());
            topicRelationService.delete(relation.getId());
        }
        return true;
    }

    @Transactional(rollbackFor = Throwable.class)
    public <T> void register(User user, List<TopicApiRegisterReq> tTopicList) throws TopicExistsException, TopicApiExistsException, UserTopicRelationExistsException {
        Topic uTopic = getUserTopicByUId(user.getId());
        if (uTopic == null) {
            addTopic(generateUTopic(user.getId()), TopicStatEnum.ONLINE.getCode());
            uTopic = getByTopic(generateUTopic(user.getId()));
        }
        for (TopicApiRegisterReq tTopic : tTopicList) {
            addTopic(tTopic.getTopic(), TopicStatEnum.ONLINE.getCode());
            Topic tTopicPO = getByTopic(tTopic.getTopic());
            if (tTopicPO == null) {
                throw new RuntimeException("topic add failed: " + JSON.toJSONString(tTopic));
            }
            topicApiService.add(tTopicPO.getId(), tTopic.getApi(), tTopic.getParams(), tTopic.getActionName());
            topicRelationService.add(user.getId(), uTopic.getId(), tTopicPO.getId());
        }
    }

    @Transactional(rollbackFor = Throwable.class)
    public <T> void applyExistsTopic(User user, List<TopicShareReq> tTopicList) throws TopicNotExistsException, UserTopicRelationExistsException, TopicExistsException {
        Topic uTopic = getUserTopicByUId(user.getId());
        if (uTopic == null) {
            addTopic(generateUTopic(user.getId()), TopicStatEnum.ONLINE.getCode());
            uTopic = getByTopic(generateUTopic(user.getId()));
        }
        for (TopicShareReq tTopic : tTopicList) {
            Topic tTopicPO = getByTopic(tTopic.getTopic());
            if (tTopicPO == null) {
                throw new TopicNotExistsException("topic add failed: " + JSON.toJSONString(tTopic));
            }
            topicRelationService.add(user.getId(), uTopic.getId(), tTopicPO.getId());
        }
    }

    private String generateUTopic(Long uid) {
        return "/usr/" + uid;
    }
}
