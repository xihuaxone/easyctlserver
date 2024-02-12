package com.xihua.easyctlserver.enums;


public enum TopicStatEnum {
    ONLINE(1),
    OFFLINE(2);

    private final int code;

    TopicStatEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static TopicStatEnum valueOf(int code) {
        for (TopicStatEnum te : TopicStatEnum.values()) {
            if (te.code == code) {
                return te;
            }
        }
        return null;
    }
}
