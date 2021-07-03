package com.wj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.dc.pr.PRError;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    private String id;
    private String title;
    private String context;
    private String description;
    private String picture;
    private String tip;
    private int visit;
    private int appreciate;
    private String isComment;
    private String isReprint;
    private int state;
    private Date createTime;
    private Date updateTime;
    private String isRecommend;
    private String typeID;
    private String typeName;
    private int status;
    private String userID;
    private String tagsName;
    private String tagsID;

}
