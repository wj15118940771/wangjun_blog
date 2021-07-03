package com.wj.dao;

import com.wj.pojo.Blog;
import com.wj.pojo.Blog_Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface Blog_TagMapper {
    void addBlog_Tag(Blog_Tag bt);
    void deleteBlog_Tag(Blog_Tag bt);
    void deleteBlog_Tag2(Blog_Tag bt);
    void updateBlog_Tag(Blog_Tag bt);
    List<Blog_Tag> listBlog_Tag(Blog_Tag bt);
    Integer countBlog_Tag(String blogid);
}
