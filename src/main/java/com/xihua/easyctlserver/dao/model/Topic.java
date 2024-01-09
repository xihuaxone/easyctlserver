package com.xihua.easyctlserver.dao.model;

import java.util.Date;

public class Topic {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic.id
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic.topic
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    private String topic;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic.stat
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    private Integer stat;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic.gmt_created
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    private Date gmtCreated;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column topic.gmt_modified
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    private Date gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic.id
     *
     * @return the value of topic.id
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic.id
     *
     * @param id the value for topic.id
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic.topic
     *
     * @return the value of topic.topic
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    public String getTopic() {
        return topic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic.topic
     *
     * @param topic the value for topic.topic
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic.stat
     *
     * @return the value of topic.stat
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    public Integer getStat() {
        return stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic.stat
     *
     * @param stat the value for topic.stat
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    public void setStat(Integer stat) {
        this.stat = stat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic.gmt_created
     *
     * @return the value of topic.gmt_created
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic.gmt_created
     *
     * @param gmtCreated the value for topic.gmt_created
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column topic.gmt_modified
     *
     * @return the value of topic.gmt_modified
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column topic.gmt_modified
     *
     * @param gmtModified the value for topic.gmt_modified
     *
     * @mbg.generated Tue Jan 09 22:39:27 CST 2024
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}