package com.wj.service.Impl;

import com.wj.dao.Update_MsgMapper;
import com.wj.pojo.Follow;
import com.wj.pojo.Update_Msg;
import com.wj.service.FollowService;
import com.wj.service.Update_MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class Update_MsgServiceImpl implements Update_MsgService {

    @Autowired
    private Update_MsgMapper update_msgMapper;

    @Autowired
    private FollowService followService;

    @Override
    public void addUpdate_Msg(Update_Msg update_msg) {

        Follow queryFollow = new Follow();
        queryFollow.setFollow(update_msg.getUserID());
        queryFollow.setType("user");
        List<Follow> list = followService.listFollow(queryFollow);
        for(Follow follow : list){
           Update_Msg add = new Update_Msg();
           add.setId(UUID.randomUUID().toString().replace("-",""));
           add.setUpdateTime(update_msg.getUpdateTime());
           add.setBlogID(update_msg.getBlogID());
           add.setType(update_msg.getType());
           add.setUserID(update_msg.getUserID());
           add.setFollower(follow.getUserID());
           update_msgMapper.addUpdate_Msg(add);
        }

    }

    @Override
    public void deleteUpdate_Msg(Update_Msg update_msg) {
        update_msgMapper.deleteUpdate_Msg(update_msg);
    }

    @Override
    public void updateUpdate_Msg(Update_Msg update_msg) {
        update_msgMapper.updateUpdate_Msg(update_msg);
    }

    @Override
    public List<Update_Msg> listUpdate_Msg(Update_Msg update_msg) {
        return update_msgMapper.listUpdate_Msg(update_msg);
    }

    @Override
    public Update_Msg getByID(String id) {
        return update_msgMapper.getByID(id);
    }

    @Override
    public int countByID(String id) {
        return update_msgMapper.countByID(id);
    }

    @Override
    public void deleteWhenUnfollow(Update_Msg update_msg) {
        update_msgMapper.deleteWhenUnfollow(update_msg);
    }

    @Override
    public void deleteAllByFollower(Update_Msg update_msg) {
        update_msgMapper.deleteAllByFollower(update_msg);
    }
}
