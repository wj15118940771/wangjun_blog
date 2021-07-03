package com.wj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Update_Msg {
    private String id;
    private String userID;
    private String blogID;
    private Date updateTime;
    private String type;
    private int status;
    private String follower;
}
