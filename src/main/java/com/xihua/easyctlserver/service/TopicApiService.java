package com.xihua.easyctlserver.service;

import com.xihua.easyctlserver.dao.mapper.TopicApiMapper;
import com.xihua.easyctlserver.dao.mapper.TopicMapper;
import com.xihua.easyctlserver.dao.mapper.UserTopicRelationMapper;
import com.xihua.easyctlserver.dao.model.*;
import com.xihua.easyctlserver.exception.TopicApiExistsException;
import com.xihua.easyctlserver.exception.TopicExistsException;
import com.xihua.easyctlserver.exception.TopicNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicApiService {

    @Autowired
    private TopicApiMapper topicApiMapper;

    public TopicApi get(Long topicApiId) {
        return topicApiMapper.selectByPrimaryKey(topicApiId);
    }

    public List<TopicApi> getByTidApi(Long tid, String api) {
        TopicApiExample example = new TopicApiExample();
        example.createCriteria().andTidEqualTo(tid).andApiEqualTo(api);
        return topicApiMapper.selectByExample(example);
    }

    public TopicApi getByTidApiParams(Long tid, String api, String params) {
        TopicApiExample example = new TopicApiExample();
        example.createCriteria().andTidEqualTo(tid).andApiEqualTo(api).andParamsEqualTo(params);
        List<TopicApi> topicApis = topicApiMapper.selectByExample(example);
        if (topicApis.isEmpty()) {
            return null;
        }
        return topicApis.get(0);
    }

    public void add(Long tid, String api, String params) throws TopicApiExistsException {
        if (getByTidApiParams(tid, api, params) != null) {
            throw new TopicApiExistsException("tid=" + tid + " api=" + api
                    + " params=" + params + " already exists.");
        }
        TopicApi topicApi = new TopicApi();
        topicApi.setTid(tid);
        topicApi.setApi(api);
        topicApi.setParams(params);
        topicApiMapper.insert(topicApi);
    }

    public boolean update(Long topicApiId, String newParams) {
        TopicApi topicApi = new TopicApi();
        topicApi.setId(topicApiId);
        topicApi.setParams(newParams);
        return topicApiMapper.updateByPrimaryKeySelective(topicApi) > 0;
    }

    public void delete(Long topicApiId) {
        topicApiMapper.deleteByPrimaryKey(topicApiId);
    }
}
