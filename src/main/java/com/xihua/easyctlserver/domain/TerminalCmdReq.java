package com.xihua.easyctlserver.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TerminalCmdReq {
    private String topic;

    private String api;

    private String params;
}
