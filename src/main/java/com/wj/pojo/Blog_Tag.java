package com.wj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog_Tag {
    private String id;
    private String blogid;
    private String tagid;
    private int status;
}
