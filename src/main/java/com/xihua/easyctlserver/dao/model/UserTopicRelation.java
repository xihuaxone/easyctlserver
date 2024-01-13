package com.xihua.easyctlserver.dao.model;

import java.util.Date;

public class UserTopicRelation {
    private Long id;

    private Long uId;

    private Long uTid;

    private Long tTid;

    private Date gmtCreated;

    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public Long getuTid() {
        return uTid;
    }

    public void setuTid(Long uTid) {
        this.uTid = uTid;
    }

    public Long gettTid() {
        return tTid;
    }

    public void settTid(Long tTid) {
        this.tTid = tTid;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}