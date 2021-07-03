package com.wj.service;

import com.wj.pojo.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {
    void addBlog(Blog blog);
    void deleteBlog(Blog blog);
    void updateBlog(Blog blog);
    List<Blog> listBlog(Blog blog);
    List<Blog> listMyselfBlog(Blog blog);
    Blog getByID(String id);
    Integer countVisit(String id);
    Integer countAppreciate(String id);
    List<Blog> topBlog( );
    List<Blog> queryBlog(String  query);
    List<Blog> rightUserBlog(String id);
    List<Blog> getAll();
    Blog getByIDWithStatus(String id);
    void updateBlogByVisit(Blog blog);
    void updateBlogByAppreciate(Blog blog);
}
