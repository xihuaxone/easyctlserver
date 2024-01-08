package com.xihua.easyctlserver.exception;

public class UserAuthAopException extends RuntimeException {
    public UserAuthAopException(String msg) {
        super(msg);
    }

    public UserAuthAopException(Throwable e) {
        super(e);
    }
}
