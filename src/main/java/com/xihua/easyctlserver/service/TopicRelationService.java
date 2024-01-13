package com.xihua.easyctlserver.service;

import com.xihua.easyctlserver.dao.mapper.UserTopicRelationMapper;
import com.xihua.easyctlserver.dao.model.UserTopicRelation;
import com.xihua.easyctlserver.dao.model.UserTopicRelationExample;
import com.xihua.easyctlserver.exception.UserTopicRelationExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicRelationService {
    @Autowired
    UserTopicRelationMapper userTopicRelationMapper;

    public List<UserTopicRelation> get(Long uid) {
        UserTopicRelationExample relationExample = new UserTopicRelationExample();
        relationExample.createCriteria().andUIdEqualTo(uid);
        return userTopicRelationMapper.selectByExample(relationExample);
    }

    public List<UserTopicRelation> getByUidTTid(Long uid, Long tTid) {
        UserTopicRelationExample example = new UserTopicRelationExample();
        example.createCriteria().andUIdEqualTo(uid).andTTidEqualTo(tTid);
        return userTopicRelationMapper.selectByExample(example);
    }

    public UserTopicRelation getByUidUTidTTid(Long uid, Long uTid, Long tTid) {
        UserTopicRelationExample example = new UserTopicRelationExample();
        example.createCriteria().andUIdEqualTo(uid).andUTidEqualTo(uTid).andTTidEqualTo(tTid);
        List<UserTopicRelation> relations = userTopicRelationMapper.selectByExample(example);
        return relations.isEmpty() ? null : relations.get(0);
    }

    public void add(Long uid, Long uTid, Long tTid) throws UserTopicRelationExistsException {
        if (getByUidUTidTTid(uid, uTid, tTid) != null) {
            throw new UserTopicRelationExistsException(
                    String.format("userTopicRelation exists: uid=%s, uTid=%s, tTid=%s", uid, uTid, tTid));
        }
        UserTopicRelation relation = new UserTopicRelation();
        relation.setuId(uid);
        relation.setuTid(uTid);
        relation.settTid(tTid);
        userTopicRelationMapper.insert(relation);
    }

    public void delete(Long id) {
        userTopicRelationMapper.deleteByPrimaryKey(id);
    }
}
