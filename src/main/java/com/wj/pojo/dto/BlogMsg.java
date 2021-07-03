package com.wj.pojo.dto;

import com.wj.pojo.Blog;
import com.wj.pojo.Comment;
import com.wj.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogMsg {

    private Blog blog;
    private User author;

}
