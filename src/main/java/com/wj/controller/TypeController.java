package com.wj.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.pojo.Blog;
import com.wj.pojo.User;
import com.wj.pojo.dto.BlogMsg;
import com.wj.service.BlogService;
import com.wj.service.ChildDictionaryService;
import com.wj.service.UserService;
import com.wj.utils.DataChange;
import com.wj.utils.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/type")
public class TypeController {

    @Autowired
    public ChildDictionaryService childDictionaryService;

    @Autowired
    public BlogService blogService;

    @Autowired
    public UserService userService;

    @GetMapping("")
    public String type(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
            Model model,HttpSession session,
                       RedirectAttributes attributes){
        Object object = session.getAttribute("user");
        User user = (User)object;

        int isLogin = 1;
        if(user!=null) {
            isLogin = 0;
        }

        model.addAttribute("typeMsg",childDictionaryService.getMessage(childDictionaryService.listByparentID("123da1")));
        model.addAttribute("activeTagId",0);

        PageHelper.startPage(pagenum, Page.pageSize,"updateTime desc");
        List<Blog> blogList = blogService.listBlog(new Blog());
        //查询结果（pageInfo信息）保存起来。pageHelper会作用于第一句查询语句
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);

        List<BlogMsg> blogMsgList = new ArrayList<>();
        for(Blog blog: blogList){
            blog.setDescription(DataChange.titleChange(blog.getDescription(),40));
            blogMsgList.add(new BlogMsg(blog,userService.getUserById(blog.getUserID())));
        }

        //在这里吧信息存到新的去
        PageInfo<BlogMsg> pageInfo1 = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo,pageInfo1);
        pageInfo1.setList(blogMsgList);

        model.addAttribute("isLogin",isLogin);
        model.addAttribute("page",pageInfo1);
        return "types";
    }

    @GetMapping("/{id}/msg")
    public String getType(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
            @PathVariable String id, HttpSession session, RedirectAttributes attributes, Model model){

        Object object = session.getAttribute("user");
        User user = (User)object;

        int isLogin = 1;
        if(user!=null) {
            isLogin = 0;
        }
        model.addAttribute("isLogin",isLogin);
        model.addAttribute("typeMsg",childDictionaryService.getMessage(childDictionaryService.listByparentID("123da1")));
        model.addAttribute("activeTagId",id);


        PageHelper.startPage(pagenum, Page.pageSize,"updateTime desc");

        Blog queryBlog = new Blog();
        queryBlog.setTypeID(id);
        List<Blog> blogList = blogService.listBlog(queryBlog);
        //查询结果（pageInfo信息）保存起来。pageHelper会作用于第一句查询语句
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);

        List<BlogMsg> blogMsgList = new ArrayList<>();
        for(Blog blog: blogList){
            blog.setDescription(DataChange.titleChange(blog.getDescription(),40));
            blogMsgList.add(new BlogMsg(blog,userService.getUserById(blog.getUserID())));
        }

        //在这里吧信息存到新的去
        PageInfo<BlogMsg> pageInfo1 = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo,pageInfo1);
        pageInfo1.setList(blogMsgList);

        model.addAttribute("page",pageInfo1);
        return "types";
    }

}
