package com.wj.pojo.vo;

import com.wj.pojo.Comment;
import com.wj.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    private Comment comment;
    private User user;
    private User target;
}
