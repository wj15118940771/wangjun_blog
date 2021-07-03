package com.wj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParentDictionary {
    private String id;
    private String name;
    private Date createTime;
    private int status;
}
