package com.xihua.easyctlserver.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Response <T> {
    private boolean success;

    private String errMsg;

    private T data;
}
