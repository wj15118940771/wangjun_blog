package com.wj.service.Impl;

import com.wj.dao.Blog_TagMapper;
import com.wj.pojo.Blog;
import com.wj.pojo.Blog_Tag;
import com.wj.service.Blog_TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class Blog_TagServiceImpl implements Blog_TagService {
    @Autowired
    private Blog_TagMapper blog_tagMapper;

    @Override
    public void addBlog_Tag(Blog_Tag bt) {
        String id = UUID.randomUUID().toString();
        bt.setId(id.replace("-",""));
        blog_tagMapper.addBlog_Tag(bt);
    }

    @Override
    public void deleteBlog_Tag(Blog_Tag bt) {
        blog_tagMapper.deleteBlog_Tag(bt);
    }

    @Override
    public void deleteBlog_Tag2(Blog_Tag bt) {
        blog_tagMapper.deleteBlog_Tag2(bt);
    }

    @Override
    public void updateBlog_Tag(Blog_Tag bt) {
        blog_tagMapper.updateBlog_Tag(bt);
    }

    @Override
    public List<Blog_Tag> listBlog_Tag(Blog_Tag bt) {
        return blog_tagMapper.listBlog_Tag(bt);
    }

    @Override
    public Integer countBlog_Tag(String blogid) {
        return blog_tagMapper.countBlog_Tag(blogid);
    }
}
