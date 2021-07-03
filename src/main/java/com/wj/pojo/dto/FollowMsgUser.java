package com.wj.pojo.dto;

import com.github.pagehelper.PageInfo;
import com.wj.pojo.Follow;
import com.wj.pojo.User;
import com.wj.pojo.vo.UpdateMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowMsgUser {
    private User user;
    private Follow follow;
    private PageInfo<UpdateMsg> updateMsgs;
}
