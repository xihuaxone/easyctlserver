package com.xihua.easyctlserver.exception;

public class TopicApiExistsException extends Exception {
    public TopicApiExistsException(String msg) {
        super(msg);
    }

    public TopicApiExistsException() {
        super();
    }
}
