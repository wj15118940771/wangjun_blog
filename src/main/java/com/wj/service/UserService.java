package com.wj.service;

import com.wj.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    void addUser(User user);
    void deleteUser(User user);
    void updateUser(User user);
    List<User> listUser(User user);
    User getUserByAccount(String account);
    User getUserById(String id);
    List<User> sumUser();
    List<User> getAll();
    User getUserByAccountUseThird(String account);
}
