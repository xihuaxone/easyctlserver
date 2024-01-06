package com.xihua.easyctlserver.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TerminalControlReq {
    private String targetTopic;

    private String api;

    private String params;
}
