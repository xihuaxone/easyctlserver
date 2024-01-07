package com.xihua.easyctlserver.service;

import com.xihua.easyctlserver.dao.mapper.UserAuthMapper;
import com.xihua.easyctlserver.dao.model.UserAuth;
import com.xihua.easyctlserver.dao.model.UserAuthExample;
import com.xihua.easyctlserver.exception.UserAuthExistsException;
import com.xihua.easyctlserver.exception.UserAuthNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    public UserAuth get(Long uid) {
        UserAuthExample example = new UserAuthExample();
        example.createCriteria().andUIdEqualTo(uid);
        List<UserAuth> userAuths = userAuthMapper.selectByExample(example);
        if (userAuths.isEmpty()) {
            return null;
        }
        return userAuths.get(0);
    }

    public UserAuth get(String loginAccount) {
        UserAuthExample example = new UserAuthExample();
        example.createCriteria().andLoginAccountEqualTo(loginAccount);
        List<UserAuth> userAuths = userAuthMapper.selectByExample(example);
        if (userAuths.isEmpty()) {
            return null;
        }
        return userAuths.get(0);
    }

    public void add(Long uid, String loginAccount, String password) throws UserAuthExistsException {
        if (get(uid) != null || get(loginAccount) != null) {
            throw new UserAuthExistsException("user loginAccount " + loginAccount + " already exists.");
        }
        UserAuth userAuth = new UserAuth();
        userAuth.setuId(uid);
        userAuth.setLoginAccount(loginAccount);
        userAuth.setLoginPassword(password);
        userAuthMapper.insert(userAuth);
    }

    public boolean update(Long uid, String newPassword) throws UserAuthNotExistsException {
        UserAuth oldAuth = get(uid);
        if (oldAuth == null) {
            throw new UserAuthNotExistsException("user " + uid + " not exists.");
        }
        UserAuth userAuth = new UserAuth();
        userAuth.setId(uid);
        userAuth.setLoginPassword(newPassword);
        return userAuthMapper.updateByPrimaryKeySelective(userAuth) > 0;
    }

    public boolean delete(Long uid) {
        return userAuthMapper.deleteByPrimaryKey(uid) > 0;
    }

    public Long auth(String loginAccount, String password) {
        UserAuthExample userAuthExample = new UserAuthExample();
        userAuthExample.createCriteria()
                .andLoginAccountEqualTo(loginAccount)
                .andLoginPasswordEqualTo(password);
        List<UserAuth> userAuths = userAuthMapper.selectByExample(userAuthExample);
        if (userAuths.isEmpty()) {
            return null;
        }
        return userAuths.get(0).getuId();
    }
}
