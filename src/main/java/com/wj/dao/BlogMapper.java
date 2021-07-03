package com.wj.dao;

import com.wj.pojo.Blog;
import com.wj.pojo.ChildDictionary;
import com.wj.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface BlogMapper {
    void addBlog(Blog blog);
    void deleteBlog(Blog blog);
    void updateBlog(Blog blog);
    List<Blog> listBlog(Blog blog);
    List<Blog> listMyselfBlog(Blog blog);
    Integer countBlog (String id);
    Blog getByID(String id);
    Integer countVisit(String id);
    Integer countAppreciate(String id);
    List<Blog> topBlog( );
    List<Blog> queryBlog(String query);
    List<Blog> rightUserBlog(String id);
    List<Blog> getAll();
    Blog getByIDWithStatus(String id);
    void updateBlogByVisit(Blog blog);
    void updateBlogByAppreciate(Blog blog);
}
