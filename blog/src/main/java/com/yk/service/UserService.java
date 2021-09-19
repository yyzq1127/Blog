package com.yk.service;

import com.yk.dao.UserMapper;
import com.yk.entity.User;
import com.yk.utils.AssertUtil;
import com.yk.utils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yk
 * @version 1.0
 * @date 2021/4/3 10:23
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User queryByUserNameAndPassword(String userName, String password) {
        User user = userMapper.queryByUserName(userName);
        AssertUtil.isTrue(user == null,"用户不存在!");
        AssertUtil.isTrue(!HashUtil.matchBC(password,user.getPassword()),"密码错误!");
        return user;
    }
}
