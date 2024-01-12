package com.xihua.easyctlserver.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopicApiDTO {
    private Long id;

    private Long tid;

    private String api;

    private String params;

    private String actionName;
}
