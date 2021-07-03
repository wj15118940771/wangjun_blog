package com.wj.service;

import com.wj.pojo.Blog;
import com.wj.pojo.Blog_Tag;

import java.util.List;

public interface Blog_TagService {
    void addBlog_Tag(Blog_Tag bt);
    void deleteBlog_Tag(Blog_Tag bt);
    void deleteBlog_Tag2(Blog_Tag bt);
    void updateBlog_Tag(Blog_Tag bt);
    List<Blog_Tag> listBlog_Tag(Blog_Tag bt);
    Integer countBlog_Tag(String id);

}
