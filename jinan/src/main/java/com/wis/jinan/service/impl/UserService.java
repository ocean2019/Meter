package com.wis.jinan.service.impl;

import	com.wis.jinan.dao.IUserDao;
import	com.wis.jinan.entity.User;
import	com.wis.jinan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import	java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserDao userDao;
    @Override
    public int add(User user) {
        return userDao.add(user);
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public int delete(int id) {
        return userDao.delete(id);
    }

    @Override
    public User findUserById(int id) {
        return userDao.findUserById(id);
    }

    @Override
    public List<User> findUserList() {
        return userDao.findUserList();
    }
}
