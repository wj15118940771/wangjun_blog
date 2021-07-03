package com.wj.dao;


import com.wj.pojo.Follow;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FollowMapper {
    void addFollow(Follow follow);
    void deleteFollow(Follow follow);
    void updateFollow(Follow follow);
    List<Follow> listFollow(Follow follow);
    List<Follow> listFollowEXblogStatus(Follow follow);
    Follow getByID(String id);
}
