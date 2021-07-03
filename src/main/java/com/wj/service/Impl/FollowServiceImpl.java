package com.wj.service.Impl;

import com.wj.dao.FollowMapper;
import com.wj.pojo.Follow;
import com.wj.pojo.Update_Msg;
import com.wj.service.FollowService;
import com.wj.service.Update_MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    public FollowMapper followMapper;

    @Autowired
    public Update_MsgService update_msgService;
    @Override
    public void addFollow(Follow follow) {
        String id = UUID.randomUUID().toString();
        follow.setId(id.replace("-",""));
        follow.setCreateTime(new Date());
        follow.setUpdateTime(new Date());
        followMapper.addFollow(follow);
    }

    @Override
    public void deleteFollow(Follow follow) {

        Follow query = followMapper.getByID(follow.getId());
        if(query.getType().equals("user")){
            Update_Msg um = new Update_Msg();
            um.setFollower(query.getUserID());
            um.setUserID(query.getFollow());
            update_msgService.deleteWhenUnfollow(um);
        }
        followMapper.deleteFollow(follow);
    }

    @Override
    public void updateFollow(Follow follow) {
        follow.setUpdateTime(new Date());
        followMapper.updateFollow(follow);
    }

    @Override
    public List<Follow> listFollow(Follow follow) {
        return followMapper.listFollow(follow);
    }

    @Override
    public List<Follow> listFollowEXblogStatus(Follow follow) {
        return followMapper.listFollowEXblogStatus(follow);
    }

    @Override
    public Follow getByID(String id) {
        return followMapper.getByID(id);
    }
}
