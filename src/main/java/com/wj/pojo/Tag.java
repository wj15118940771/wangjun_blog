package com.wj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private String id;
    private String name;
    private String userID;
    private int status;
    private Date createTime;
    private Date updateTime;

}
