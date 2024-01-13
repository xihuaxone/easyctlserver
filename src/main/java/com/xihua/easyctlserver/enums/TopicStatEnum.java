package com.xihua.easyctlserver.enums;

public enum TopicStatEnum {
    ONLINE(1),
    OFFLINE(0);

    private final int code;

    TopicStatEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
