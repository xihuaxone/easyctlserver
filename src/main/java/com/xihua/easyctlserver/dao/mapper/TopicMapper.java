package com.xihua.easyctlserver.dao.mapper;

import com.xihua.easyctlserver.dao.model.Topic;
import com.xihua.easyctlserver.dao.model.TopicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TopicMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated Sun Jan 14 00:12:00 CST 2024
     */
    long countByExample(TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated Sun Jan 14 00:12:00 CST 2024
     */
    int deleteByExample(TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated Sun Jan 14 00:12:00 CST 2024
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated Sun Jan 14 00:12:00 CST 2024
     */
    int insert(Topic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated Sun Jan 14 00:12:00 CST 2024
     */
    int insertSelective(Topic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated Sun Jan 14 00:12:00 CST 2024
     */
    List<Topic> selectByExample(TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated Sun Jan 14 00:12:00 CST 2024
     */
    Topic selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated Sun Jan 14 00:12:00 CST 2024
     */
    int updateByExampleSelective(@Param("record") Topic record, @Param("example") TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated Sun Jan 14 00:12:00 CST 2024
     */
    int updateByExample(@Param("record") Topic record, @Param("example") TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated Sun Jan 14 00:12:00 CST 2024
     */
    int updateByPrimaryKeySelective(Topic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table topic
     *
     * @mbg.generated Sun Jan 14 00:12:00 CST 2024
     */
    int updateByPrimaryKey(Topic record);
}