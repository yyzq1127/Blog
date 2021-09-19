package com.yk.dao;


import com.yk.entity.User;

public interface UserMapper {
    User queryByUserName(String userName);

}
