package com.xihua.easyctlserver.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response <T> {
    private boolean success;

    private String errMsg;

    private T data;

    public Response() {
    }

    public Response(boolean success) {
        this.success = success;
    }

    public Response(boolean success, String errMsg) {
        this.success = success;
        this.errMsg = errMsg;
    }

    public Response(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Response(boolean success, String errMsg, T data) {
        this.success = success;
        this.errMsg = errMsg;
        this.data = data;
    }
}
