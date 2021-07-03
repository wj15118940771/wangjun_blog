package com.wj.service;

import com.wj.pojo.Update_Msg;

import java.util.List;

public interface Update_MsgService {
    void addUpdate_Msg(Update_Msg update_msg);
    void deleteUpdate_Msg(Update_Msg update_msg);
    void updateUpdate_Msg(Update_Msg update_msg);
    List<Update_Msg> listUpdate_Msg(Update_Msg update_msg);
    Update_Msg getByID(String id);
    int countByID(String id);
    void deleteWhenUnfollow(Update_Msg update_msg);
    void deleteAllByFollower(Update_Msg update_msg);
}
