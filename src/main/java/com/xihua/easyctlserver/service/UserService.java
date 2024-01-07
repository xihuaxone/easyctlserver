package com.xihua.easyctlserver.service;

import com.xihua.easyctlserver.dao.mapper.UserMapper;
import com.xihua.easyctlserver.dao.model.User;
import com.xihua.easyctlserver.dao.model.UserExample;
import com.xihua.easyctlserver.exception.UserExistsException;
import com.xihua.easyctlserver.exception.UserNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User get(Long uid) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(uid);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public User get(String nickName) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNickNameEqualTo(nickName);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public User add(String nickName) throws UserExistsException {
        if (get(nickName) != null) {
            throw new UserExistsException("user " + nickName + " already exists.");
        }
        User newUser = new User();
        newUser.setNickName(nickName);
        if (userMapper.insert(newUser) <= 0) {
            return null;
        }
        return get(nickName);
    }

    public boolean update(Long uid, String nickName) throws UserNotExistsException {
        if (get(uid) != null) {
            throw new UserNotExistsException("user " + uid + " not exists.");
        }
        User newUser = new User();
        newUser.setNickName(nickName);
        return userMapper.updateByPrimaryKeySelective(newUser) > 0;
    }

    public boolean delete(Long uid) {
        return userMapper.deleteByPrimaryKey(uid) > 0;
    }
}
