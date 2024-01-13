package com.xihua.easyctlserver.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopicApiRegisterReq {
    private String topic;

    private String api;

    private String params;

    private String actionName;
}
