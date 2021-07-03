package com.wj.service.Impl;

import com.wj.dao.UserMapper;
import com.wj.pojo.User;
import com.wj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(User user) {
        if(user.getId()==null||user.getId()==""){
            user.setId(UUID.randomUUID().toString().replace("-",""));
        }
        user.setCreateTime(new Date());
        userMapper.addUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userMapper.deleteUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    @Override
    public List<User> listUser(User user) {
        return userMapper.listUser(user);
    }

    @Override
    public User getUserByAccount(String account) {
        return userMapper.getUserByAccount(account);
    }

    @Override
    public User getUserById(String id) {
        return userMapper.getUserById(id);
    }

    @Override
    public List<User> sumUser() {
        return userMapper.sumUser();
    }

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public User getUserByAccountUseThird(String account) {
        return userMapper.getUserByAccountUseThird(account);
    }
}
