package com.wj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String id;
    private String context;
    private Date createTime;
    private String target;
    private String blogID;
    private String userID;
    private int floor;
    private int status;
    private int parentFloor;
}
