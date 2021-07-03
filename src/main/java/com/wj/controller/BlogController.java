package com.wj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.pojo.*;
import com.wj.pojo.dto.BlogMsg;
import com.wj.pojo.dto.CommentMsg;
import com.wj.pojo.vo.Comments;
import com.wj.service.*;
import com.wj.utils.DataChange;
import com.wj.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    public BlogService blogService;

    @Autowired
    public ChildDictionaryService childDictionaryService;

    @Autowired
    public TagService tagService;

    @Autowired
    public UserService userService;

    @Autowired
    public CommentService commentService;



    @GetMapping
    public String getBlog(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, HttpSession session, Model model){
        Object object = session.getAttribute("user");
        User user = (User)object;
        Blog queryBlog= new Blog();
        queryBlog.setUserID(user.getId());
        PageHelper.startPage(pagenum, Page.pageSize);
        PageInfo<Blog> pageInfo =new PageInfo<>(blogService.listMyselfBlog(queryBlog));
        model.addAttribute("page",pageInfo);
        return "blog_admin";
    }

    @GetMapping("/blog_admin")
    public String getAdmin(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                           HttpSession session, Model model) throws InvocationTargetException, IllegalAccessException {
        Object object = session.getAttribute("user");
        User user = (User)object;
        Blog queryBlog= new Blog();
        queryBlog.setUserID(user.getId());
        PageHelper.startPage(pagenum, Page.pageSize);
        PageInfo<Blog> pageInfo =new PageInfo<>(blogService.listMyselfBlog(queryBlog));
        model.addAttribute("page",pageInfo);
        return "blog_admin";
    }

    @GetMapping("/blog_input")
    public String newBlog(Model model, HttpSession session){
        Object object = session.getAttribute("user");
        User user = (User)object;
        Tag queryTag = new Tag();
        queryTag.setUserID(user.getId());
        model.addAttribute("blog",new Blog());
        model.addAttribute("type",childDictionaryService.listByparentID("123da1"));
        model.addAttribute("tag",tagService.listTag(queryTag));
        return "blog_input";
    }

    @PostMapping("/blog_input")
    public String addBlog(Blog blog, Model model,
                          RedirectAttributes attributes,
                          HttpSession session,String tagIds){
        Object object = session.getAttribute("user");
        User user = (User)object;



        StringBuilder tagsName = new StringBuilder();
        if(blog.getIsReprint().equals("")){
            blog.setIsRecommend("原创");
        }
        if(blog.getIsRecommend()==null){
            blog.setIsRecommend("no");
        }
        if(blog.getIsComment()==null){
            blog.setIsComment("no");
        }
        if(blog.getTagsID()!=null && blog.getTagsID()!=""){
            List<String> tagsID= DataChange.listTagNameorID(blog.getTagsID());

            for(String id : tagsID){
                Tag tag = tagService.getByID(id);
                tagsName.append(tag.getName()+",");
            }
        }
        ChildDictionary type = childDictionaryService.getByID(blog.getTypeID());
        blog.setTagsName(tagsName.toString());
        blog.setPicture(type.getPicture());
        if(blog.getId()!=null&&blog.getId()!=""){

            Blog oldblog = blogService.getByID(blog.getId());
            blog.setVisit(oldblog.getVisit());
            blog.setAppreciate(oldblog.getAppreciate());
            blog.setUserID(user.getId());
            blog.setTypeName(type.getName());

            blogService.updateBlog(blog);
            attributes.addFlashAttribute("message","更新成功~");
        }else {

            blog.setUserID(user.getId());
            List<ChildDictionary> list = childDictionaryService.listByparentID("123da1");
            blog.setTypeName(type.getName());
            blogService.addBlog(blog);
            attributes.addFlashAttribute("message","博客新增成功~");

        }

        return "redirect:/blog/blog_admin";
    }

    @GetMapping("/{id}/updateBlog")
    public String updateBlog(@PathVariable String id, HttpSession session,RedirectAttributes attributes, Model model){
        Object object = session.getAttribute("user");
        User user = (User)object;
        Tag queryTag = new Tag();
        queryTag.setUserID(user.getId());

        Blog queryBlog= blogService.getByID(id);
        if(queryBlog!=null){
            model.addAttribute("blog",queryBlog);
            model.addAttribute("type",childDictionaryService.listByparentID("123da1"));
            model.addAttribute("tag",tagService.listTag(queryTag));
            return "blog_input";
        }else return "error/error";

    }

    @GetMapping("/{id}/deleteBlog")
    public String deleteBlog(@PathVariable String id,RedirectAttributes attributes, HttpSession session){
        Object object = session.getAttribute("user");
        User user = (User)object;
        try {
            Blog blog = new Blog();
            blog.setId(id);
            blogService.deleteBlog(blog);
            attributes.addFlashAttribute("message","删除成功~");
        }catch (Exception e){
            System.out.println(e.toString());
            attributes.addFlashAttribute("message","删除失败~");
        }

        return "redirect:/blog/blog_admin";
    }


}
