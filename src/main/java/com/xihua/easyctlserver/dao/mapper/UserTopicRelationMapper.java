package com.xihua.easyctlserver.dao.mapper;

import com.xihua.easyctlserver.dao.model.UserTopicRelation;
import com.xihua.easyctlserver.dao.model.UserTopicRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserTopicRelationMapper {
    long countByExample(UserTopicRelationExample example);

    int deleteByExample(UserTopicRelationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserTopicRelation record);

    int insertSelective(UserTopicRelation record);

    List<UserTopicRelation> selectByExample(UserTopicRelationExample example);

    UserTopicRelation selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserTopicRelation record, @Param("example") UserTopicRelationExample example);

    int updateByExample(@Param("record") UserTopicRelation record, @Param("example") UserTopicRelationExample example);

    int updateByPrimaryKeySelective(UserTopicRelation record);

    int updateByPrimaryKey(UserTopicRelation record);
}