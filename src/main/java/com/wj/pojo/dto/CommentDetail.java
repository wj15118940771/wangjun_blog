package com.wj.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDetail {
    private int parentPagenum;
    private String parentid;
    private int childPagenum;
}
