package com.xihua.easyctlserver.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq {
    private String loginAccount;

    private String password;
}
