package com.xihua.easyctlserver.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicUpdateReq {

    private Long topicApiId;

    private String api;

    private String params;

    private int status;
}
