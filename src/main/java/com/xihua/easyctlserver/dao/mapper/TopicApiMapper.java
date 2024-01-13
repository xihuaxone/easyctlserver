package com.xihua.easyctlserver.dao.mapper;

import com.xihua.easyctlserver.dao.model.TopicApi;
import com.xihua.easyctlserver.dao.model.TopicApiExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TopicApiMapper {
    long countByExample(TopicApiExample example);

    int deleteByExample(TopicApiExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TopicApi record);

    int insertSelective(TopicApi record);

    List<TopicApi> selectByExample(TopicApiExample example);

    TopicApi selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TopicApi record, @Param("example") TopicApiExample example);

    int updateByExample(@Param("record") TopicApi record, @Param("example") TopicApiExample example);

    int updateByPrimaryKeySelective(TopicApi record);

    int updateByPrimaryKey(TopicApi record);
}