package com.wj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    private String id;
    private String follow;
    private String userID;
    private String type;
    private Date createTime;
    private Date updateTime;
    private int status;
}
