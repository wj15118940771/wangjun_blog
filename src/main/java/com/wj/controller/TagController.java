package com.wj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.pojo.Blog;
import com.wj.pojo.Blog_Tag;
import com.wj.pojo.Tag;
import com.wj.pojo.User;
import com.wj.pojo.dto.BlogMsg;
import com.wj.service.BlogService;
import com.wj.service.Blog_TagService;
import com.wj.service.TagService;
import com.wj.utils.DataChange;
import com.wj.utils.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private Blog_TagService blog_tagService;


    @GetMapping("")
    public String tags(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                       HttpSession session, Model model){
        PageHelper.startPage(pagenum, Page.pageSize);
        Object object = session.getAttribute("user");
        User user = (User)object;
        Tag queryTag=new Tag();
        queryTag.setUserID(user.getId());
        PageInfo<Tag> pageInfo = new PageInfo<>( tagService.listTag(queryTag));
        model.addAttribute("page",pageInfo);
        return "tag_admin";
    }

    @GetMapping("/taginput")
    public String getInput(Model model){
        model.addAttribute("tag",new Tag());
        return "tag_input";
    }

    @PostMapping("/taginput")
    public String input(@Valid Tag tag, BindingResult result,
                       HttpSession session,
                       RedirectAttributes attributes){
        Object object = session.getAttribute("user");
        User user = (User)object;
        tag.setUserID(user.getId());
        List<Tag> queryTagList = tagService.listTag(tag);
        if(queryTagList != null && queryTagList.size()>0) {
            result.rejectValue("name","nameError","标签重复~");
            return "tag_input";
        }
        if(result.hasErrors()){
            return "tag_input";
        }
        if(tag==null){
            attributes.addFlashAttribute("message","添加失败~");
            return "redirect:/tag/tag_input";
        }else {
            if(tag.getId()!=null&&!tag.getId().equals("")){
                tagService.updateTag(tag);
                attributes.addFlashAttribute("message","更新成功~");
            }else {
                tagService.addTag(tag);
                attributes.addFlashAttribute("message","添加成功~");
            }

            return "redirect:/tag";
        }

    }

    @GetMapping("/{id}/edit")
    public String editInput(@PathVariable String id, Model model){
        Tag queryTag = new Tag();
        queryTag.setId(id);
        List<Tag> list = tagService.listTag(queryTag);
        model.addAttribute("tag",list.get(0));
        return "tag_input";
    }

    @GetMapping("/count")
    public String type(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                       Model model,HttpSession session,
                       RedirectAttributes attributes){
        Object object = session.getAttribute("user");
        User user = (User)object;
        
        Tag queryTag = new Tag();
        queryTag.setUserID(user.getId());
        
        List<Tag> list = tagService.listTag(queryTag);
        model.addAttribute("tagMsg",tagService.getMessage(list));
        model.addAttribute("activeTagId",0);

        PageHelper.startPage(pagenum, Page.pageSize,"updateTime desc");
        Blog queryBlog = new Blog();
        queryBlog.setUserID(user.getId());
        List<Blog> blogList = blogService.listBlog(queryBlog);
        //查询结果（pageInfo信息）保存起来。pageHelper会作用于第一句查询语句
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);

        List<BlogMsg> blogMsgList = new ArrayList<>();
        for(Blog blog: blogList){
            blog.setDescription(DataChange.titleChange(blog.getDescription(),40));
            blogMsgList.add(new BlogMsg(blog,user));
        }

        //在这里吧信息存到新的去
        PageInfo<BlogMsg> pageInfo1 = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo,pageInfo1);
        pageInfo1.setList(blogMsgList);

        model.addAttribute("page",pageInfo1);
        
        return "tags";
    }

    @GetMapping("/{id}/msg")
    public String tagcount(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                           @PathVariable String id, HttpSession session, RedirectAttributes attributes, Model model){
        Object object = session.getAttribute("user");
        User user = (User)object;

        Tag queryTag = new Tag();
        queryTag.setUserID(user.getId());

        List<Tag> list = tagService.listTag(queryTag);
        model.addAttribute("tagMsg",tagService.getMessage(list));

        model.addAttribute("activeTagId",id);

        PageHelper.startPage(pagenum, Page.pageSize);
        Blog_Tag queryBt = new Blog_Tag();
        queryBt.setTagid(id);
        List<Blog_Tag> listBt = blog_tagService.listBlog_Tag(queryBt);

        List<Blog> blogList = new ArrayList<>();
        for(Blog_Tag bt : listBt){
            blogList.add(blogService.getByID(bt.getBlogid()));
        }


        //查询结果（pageInfo信息）保存起来。pageHelper会作用于第一句查询语句
        PageInfo<Blog_Tag> pageInfo = new PageInfo<>(listBt);

        List<BlogMsg> blogMsgList = new ArrayList<>();
        for(Blog blog: blogList){
            blog.setDescription(DataChange.titleChange(blog.getDescription(),40));
            blogMsgList.add(new BlogMsg(blog,user));
        }

        //在这里吧信息存到新的去
        PageInfo<BlogMsg> pageInfo1 = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo,pageInfo1);
        pageInfo1.setList(blogMsgList);

        model.addAttribute("page",pageInfo1);

        return "tags";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id, Model model, HttpSession session, RedirectAttributes attributes){
        Tag queryTag = new Tag();
        queryTag.setId(id);
        tagService.deleteTag(queryTag);
        attributes.addAttribute("message","删除成功");
        return "redirect:/tag";
    }

}
