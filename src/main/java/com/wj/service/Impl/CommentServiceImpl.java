package com.wj.service.Impl;

//import com.wj.WebSocket.WebSocketService;
import com.wj.dao.CommentMapper;
import com.wj.pojo.Comment;
import com.wj.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

//    @Autowired
//    WebSocketService webSocketService;

    @Override
    public void addComment(Comment comment) throws IOException {
        String id = UUID.randomUUID().toString().replace("-","");
        comment.setId(id);
        comment.setCreateTime(new Date());
        commentMapper.addComment(comment);

//        webSocketService.sendMessage(comment.getContext(),comment.getTarget());
    }

    @Override
    public void deleteComment(Comment comment) {
        commentMapper.deleteComment(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        commentMapper.updateComment(comment);
    }

    @Override
    public List<Comment> listComment(Comment comment) {
        return commentMapper.listComment(comment);
    }

    @Override
    public Integer countComment(String id) {
        return commentMapper.countComment(id);
    }

    @Override
    public Integer countComment2(Comment comment) {
        return commentMapper.countComment2(comment);
    }

    @Override
    public Comment getCommentById(String id) {
        return commentMapper.getCommentById(id);
    }
}
