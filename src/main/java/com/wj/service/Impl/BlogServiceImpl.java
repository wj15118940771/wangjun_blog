package com.wj.service.Impl;

import com.wj.dao.BlogMapper;
import com.wj.pojo.Blog;
import com.wj.pojo.Blog_Tag;
import com.wj.pojo.Tag;
import com.wj.pojo.Update_Msg;
import com.wj.service.BlogService;
import com.wj.service.Blog_TagService;
import com.wj.service.TagService;
import com.wj.service.Update_MsgService;
import com.wj.utils.DataChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private Blog_TagService blog_tagService;

    @Autowired
    public Update_MsgService update_msgService;

    @Override
    public void addBlog(Blog blog) {
        String id = UUID.randomUUID().toString().replace("-","");
        blog.setId(id);
        blog.setCreateTime(new Date());
        Date update = new Date();
        blog.setUpdateTime(update);


        if(blog.getTagsID()!=null && blog.getTagsID()!=""){
            List<String> tagsID= DataChange.listTagNameorID(blog.getTagsID());

            for(String tagid : tagsID){
                Blog_Tag blogTag = new Blog_Tag();
                blogTag.setBlogid(id);
                blogTag.setTagid(tagid);
                blog_tagService.addBlog_Tag(blogTag);
            }
        }

        if(blog.getState()==1){
            Update_Msg updateMsg = new Update_Msg();
            updateMsg.setUpdateTime(update);
            updateMsg.setBlogID(blog.getId());
            updateMsg.setUserID(blog.getUserID());
            updateMsg.setType("新博客");
            update_msgService.addUpdate_Msg(updateMsg);
        }

        blogMapper.addBlog(blog);
    }

    @Override
    public void deleteBlog(Blog blog) {
        blogMapper.deleteBlog(blog);
    }

    @Override
    public void updateBlog(Blog blog) {

        Date update = new Date();
        blog.setUpdateTime(update);

        Blog_Tag deleteblog_tag = new Blog_Tag();
        deleteblog_tag.setBlogid(blog.getId());
        blog_tagService.deleteBlog_Tag2(deleteblog_tag);

        if(blog.getTagsID()!=null && blog.getTagsID()!=""){
            List<String> tagsID= DataChange.listTagNameorID(blog.getTagsID());

            for(String tagid : tagsID){
                Blog_Tag blogTag = new Blog_Tag();
                blogTag.setId(UUID.randomUUID().toString().replace("-",""));
                blogTag.setBlogid(blog.getId());
                blogTag.setTagid(tagid);
                blog_tagService.addBlog_Tag(blogTag);
            }
        }

        if(blog.getState()==1&&blog.getUserID()!=null){
            Update_Msg updateMsg = new Update_Msg();
            updateMsg.setUpdateTime(update);
            updateMsg.setBlogID(blog.getId());
            updateMsg.setUserID(blog.getUserID());
            updateMsg.setType("更新");
            update_msgService.addUpdate_Msg(updateMsg);
        }

        blogMapper.updateBlog(blog);
    }

    @Override
    public List<Blog> listBlog(Blog blog) {
        return blogMapper.listBlog(blog);
    }

    @Override
    public List<Blog> listMyselfBlog(Blog blog) {
        return blogMapper.listMyselfBlog(blog);
    }

    @Override
    public Blog getByID(String id) {
        return blogMapper.getByID(id);
    }

    @Override
    public Integer countVisit(String id) {
        return blogMapper.countVisit(id);
    }

    @Override
    public Integer countAppreciate(String id) {
        return blogMapper.countAppreciate(id);
    }

    @Override
    public List<Blog> topBlog() {
        return blogMapper.topBlog();
    }

    @Override
    public List<Blog> queryBlog(String query) {
        return blogMapper.queryBlog(query);
    }

    @Override
    public List<Blog> rightUserBlog(String id) {
        return blogMapper.rightUserBlog(id);
    }

    @Override
    public List<Blog> getAll() {
        return blogMapper.getAll();
    }

    @Override
    public Blog getByIDWithStatus(String id) {
        return blogMapper.getByIDWithStatus(id);
    }

    @Override
    public void updateBlogByVisit(Blog blog) {
        blogMapper.updateBlogByVisit(blog);
    }

    @Override
    public void updateBlogByAppreciate(Blog blog) {
        blogMapper.updateBlogByAppreciate(blog);
    }


}
