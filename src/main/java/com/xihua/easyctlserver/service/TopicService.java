package com.xihua.easyctlserver.service;

import com.xihua.easyctlserver.dao.mapper.TopicMapper;
import com.xihua.easyctlserver.dao.mapper.UserMapper;
import com.xihua.easyctlserver.dao.mapper.UserTopicRelationMapper;
import com.xihua.easyctlserver.dao.model.*;
import com.xihua.easyctlserver.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private UserTopicRelationMapper userTopicRelationMapper;

    public Topic get(Long tid) {
        return topicMapper.selectByPrimaryKey(tid);
    }

    public Topic get(String topic) {
        TopicExample example = new TopicExample();
        example.createCriteria().andTopicEqualTo(topic);
        List<Topic> topics = topicMapper.selectByExample(example);
        if (topics.isEmpty()) {
            return null;
        }
        return topics.get(0);
    }

    public Topic getUserTopicByUId(Long uid) {
        UserTopicRelationExample relationExample = new UserTopicRelationExample();
        relationExample.createCriteria().andUIdEqualTo(uid);
        List<UserTopicRelation> relations = userTopicRelationMapper.selectByExample(relationExample);
        for (UserTopicRelation relation : relations) {
            return get(relation.getuTid());
        }
        return null;
    }

    public List<Topic> getTTopicsByUId(Long uid) {
        UserTopicRelationExample relationExample = new UserTopicRelationExample();
        relationExample.createCriteria().andUIdEqualTo(uid);
        List<UserTopicRelation> relations = userTopicRelationMapper.selectByExample(relationExample);
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
        UserTopicRelationExample relationExample = new UserTopicRelationExample();
        relationExample.createCriteria().andUIdEqualTo(uid).andTTidEqualTo(topicInfo.getId());
        List<UserTopicRelation> relations = userTopicRelationMapper.selectByExample(relationExample);
        List<Topic> collect = relations.stream().map(r -> get(r.gettTid())).collect(Collectors.toList());
        return collect.isEmpty() ? null : collect.get(0);
    }

    public Topic getTopicByUIdTid(Long uid, Long tId) {
        UserTopicRelationExample relationExample = new UserTopicRelationExample();
        relationExample.createCriteria().andUIdEqualTo(uid).andTTidEqualTo(tId);
        List<UserTopicRelation> relations = userTopicRelationMapper.selectByExample(relationExample);
        List<Topic> collect = relations.stream().map(r -> get(r.gettTid())).collect(Collectors.toList());
        return collect.isEmpty() ? null : collect.get(0);
    }

    private void addTopic(String topic, int status) throws TopicExistsException {
        if (get(topic) != null) {
            throw new TopicExistsException("topic " + topic + " already exists.");
        }
        Topic record = new Topic();
        record.setTopic(topic);
        record.setStat(status);
        topicMapper.insert(record);
    }

    public void add(Long uid, Topic uTopic, List<Topic> tTopicList) throws TopicExistsException {
        addTopic(uTopic.getTopic(), uTopic.getStat());
        for (Topic topic : tTopicList) {
            addTopic(topic.getTopic(), topic.getStat());
        }

        for (Topic tTopic : tTopicList) {
            UserTopicRelationExample relationExample = new UserTopicRelationExample();
            relationExample.createCriteria().andUIdEqualTo(uid).andUTidEqualTo(uTopic.getId()).andTTidEqualTo(tTopic.getId());
            if (userTopicRelationMapper.countByExample(relationExample) > 0) {
                return;
            }
            UserTopicRelation record = new UserTopicRelation();
            record.setuId(uid);
            record.setuTid(uTopic.getId());
            record.settTid(tTopic.getId());
            userTopicRelationMapper.insert(record);
        }
    }

    public boolean update(Long tid, int state) throws TopicNotExistsException {
        if (get(tid) != null) {
            throw new TopicNotExistsException("topic " + tid + " not exists.");
        }
        Topic newTopic = new Topic();
        newTopic.setId(tid);
        newTopic.setStat(state);
        return topicMapper.updateByPrimaryKeySelective(newTopic) > 0;
    }

    public boolean delete(Long tid) {
        return topicMapper.deleteByPrimaryKey(tid) > 0;
    }

    public boolean deleteAll(Long uid) {
        // 查询relation，获取所有topic
        UserTopicRelationExample example = new UserTopicRelationExample();
        example.createCriteria().andUIdEqualTo(uid);
        List<UserTopicRelation> relations = userTopicRelationMapper.selectByExample(example);
        boolean hasError = false;
        for (UserTopicRelation relation : relations) {
            delete(relation.getuTid());
            delete(relation.gettTid());
            userTopicRelationMapper.deleteByPrimaryKey(relation.getId());
        }
        return true;
    }
}
