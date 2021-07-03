package com.wj.dao;

import com.wj.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface UserMapper {
    void addUser(User user);
    void deleteUser(User user);
    void updateUser(User user);
    List<User> listUser(User user);
    User getUserByAccount(String account);
    User getUserById(String id);
    List<User> sumUser();
    List<User> rightUserBlog(String id);
    List<User> getAll();
    User getUserByAccountUseThird(String account);

}
