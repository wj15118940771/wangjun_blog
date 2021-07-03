package com.wj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildDictionary {
    private String id;
    private String name;
    private String parentID;
    private Date createTime;
    private int status;
    private String picture;
}
