package com.yk.dao;


import com.yk.entity.Friend;

import java.util.List;

public interface FriendMapper {

    List<Friend> getFriendsByQuery();

    Integer updatePublished(long id, boolean published);

    Integer addFriend(Friend friend);

    Integer updateFriend(Friend friend);

    Integer deleteFriendById(Long id);
}
