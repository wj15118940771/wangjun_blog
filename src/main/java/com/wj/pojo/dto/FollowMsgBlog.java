package com.wj.pojo.dto;

import com.wj.pojo.Blog;
import com.wj.pojo.Follow;
import com.wj.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowMsgBlog {
    private Blog blog;
    private Follow follow;
}
