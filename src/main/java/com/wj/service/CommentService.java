package com.wj.service;

import com.wj.pojo.Comment;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CommentService {
    void addComment(Comment comment) throws IOException;
    void deleteComment(Comment comment);
    void updateComment(Comment comment);
    List<Comment> listComment(Comment comment);
    Integer countComment(String id);
    Integer countComment2(Comment comment);
    Comment getCommentById(String id);
}
