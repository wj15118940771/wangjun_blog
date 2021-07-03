package com.wj.pojo.dto;


import com.github.pagehelper.PageInfo;
import com.wj.pojo.Comment;
import com.wj.pojo.vo.Comments;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentMsg {
    private Comments parentComment ;
    private List<Comments> childComment;
    PageInfo<Comments> childPageInFo;
}
