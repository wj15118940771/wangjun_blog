package com.wj.service;

import com.wj.pojo.Follow;

import java.util.List;

public interface FollowService {
    void addFollow(Follow follow);
    void deleteFollow(Follow follow);
    void updateFollow(Follow follow);
    List<Follow> listFollow(Follow follow);
    List<Follow> listFollowEXblogStatus(Follow follow);
    Follow getByID(String id);
}
