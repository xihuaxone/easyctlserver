package com.xihua.easyctlserver.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterReq {
    private String nickName;

    private String loginAccount;

    private String password;
}
