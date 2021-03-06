package com.wis.jinan.service;

import	com.wis.jinan.entity.User;

import	java.util.List;

public interface IUserService {
    int add(User user);

    int update(User user);

    int delete(int id);

    User findUserById(int id);

    List<User> findUserList();
}
