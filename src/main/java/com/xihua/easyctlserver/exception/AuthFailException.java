package com.xihua.easyctlserver.exception;

public class AuthFailException extends Exception {
    public AuthFailException(String msg) {
        super(msg);
    }

    public AuthFailException() {
        super();
    }

    public AuthFailException(Throwable e) {
        super(e);
    }
}
