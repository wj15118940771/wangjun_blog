package com.wj.dao;


import com.wj.pojo.Update_Msg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface Update_MsgMapper {
    void addUpdate_Msg(Update_Msg update_msg);
    void deleteUpdate_Msg(Update_Msg update_msg);
    void updateUpdate_Msg(Update_Msg update_msg);
    List<Update_Msg> listUpdate_Msg(Update_Msg update_msg);
    Update_Msg getByID(String id);
    int countByID(String id);
    void deleteWhenUnfollow(Update_Msg update_msg);
    void deleteAllByFollower(Update_Msg update_msg);

}
