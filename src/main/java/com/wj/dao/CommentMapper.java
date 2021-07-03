package com.wj.dao;

import com.wj.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface CommentMapper {
    void addComment(Comment comment);
    void deleteComment(Comment comment);
    void updateComment(Comment comment);
    List<Comment> listComment(Comment comment);
    Integer countComment(String id);
    Integer countComment2(Comment comment);
    Comment getCommentById(String id);
}
