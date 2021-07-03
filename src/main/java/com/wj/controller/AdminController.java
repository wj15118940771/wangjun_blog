package com.wj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.pojo.Blog;
import com.wj.pojo.ChildDictionary;
import com.wj.pojo.User;
import com.wj.service.BlogService;
import com.wj.service.ChildDictionaryService;
import com.wj.service.TagService;
import com.wj.service.UserService;
import com.wj.utils.DataChange;
import com.wj.utils.MD5Utils;
import com.wj.utils.Page;
import com.wj.utils.Static;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public BlogService blogService;

    @Autowired
    public ChildDictionaryService childDictionaryService;

    @Autowired
    public UserService userService;


    @GetMapping("/user")
    public String useradmin(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model ,
                            RedirectAttributes redirectAttributes,HttpSession session){

        PageHelper.startPage(pagenum, Page.pageSize);
        PageInfo<User> pageInfo =new PageInfo<>(userService.getAll());
        model.addAttribute("page",pageInfo);

        model.addAttribute("message",model.getAttribute("message"));
        return "/admin/userlist";
    }

    @GetMapping("/user/type")
    public String usertype(@RequestParam String userid, Model model , HttpSession session){

        User user = userService.getUserById(userid);
        if (user.getType()==1){
            user.setType(0);
        }else user.setType(1);
        userService.updateUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/user/status")
    public String userstatus(@RequestParam String userid, Model model , HttpSession session){

        User user = userService.getUserById(userid);
        if (user.getStatus()==1){
            user.setStatus(0);
        }else user.setStatus(1);
        userService.updateUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/user/reset")
    public String userreset(@RequestParam String userid, Model model , HttpSession session, RedirectAttributes redirectAttributes){

        User user = userService.getUserById(userid);
        user.setPassword(MD5Utils.code("123456"));
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("message","重置成功");
        return "redirect:/admin/user";
    }

    @GetMapping("/blog")
    public String blogadmin(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model ,
                            RedirectAttributes redirectAttributes,HttpSession session){

        PageHelper.startPage(pagenum, Page.pageSize);
        PageInfo<Blog> pageInfo =new PageInfo<>(blogService.getAll());
        model.addAttribute("page",pageInfo);
        model.addAttribute("message",model.getAttribute("message"));
        return "/admin/bloglist";
    }

    @GetMapping("/blog/status")
    public String blogstatus(@RequestParam String blogid, Model model , HttpSession session){

        Blog blog = blogService.getByIDWithStatus(blogid);
        if (blog.getStatus()==1){
            blog.setStatus(0);
        }else blog.setStatus(1);
        blogService.updateBlog(blog);
        return "redirect:/admin/blog";
    }

    @GetMapping("/type")
    public String typeadmin(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model ,
                            RedirectAttributes redirectAttributes,HttpSession session){

        PageHelper.startPage(pagenum, Page.pageSize);
        PageInfo<ChildDictionary> pageInfo =new PageInfo<>(childDictionaryService.getAllType());
        model.addAttribute("page",pageInfo);
        model.addAttribute("message",model.getAttribute("message"));
        return "/admin/typelist";
    }

    @GetMapping("/type/status")
    public String typestatus(@RequestParam String typeid, Model model , HttpSession session){

        ChildDictionary type = childDictionaryService.getByID(typeid);
        if (type.getStatus()==1){
            type.setStatus(0);
        }else type.setStatus(1);
        childDictionaryService.updateChildDictionary(type);
        return "redirect:/admin/type";
    }

    @PostMapping("/type/saveOrUpdate")
    public String saveOrUpdate( String typename, String typeid, @PathVariable MultipartFile image, Model model ,
                                HttpSession session,RedirectAttributes redirectAttributes){
        String filePath="";
        if(image!=null&&!image.isEmpty()){
            String geshi ="."+ DataChange.getFormat( image.getOriginalFilename());
            String imageName = UUID.randomUUID().toString().replace("-","")+geshi;
            filePath = Static.typePath +"/"+imageName;
            try {
                image.transferTo(new File(Static.typeFilePath+"/"+filePath));

            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }

        if(!typeid.equals("")){
            ChildDictionary updateType = childDictionaryService.getByID(typeid);
            updateType.setName(typename);
            if(!filePath.equals("")){
                updateType.setPicture(filePath);
            }
            childDictionaryService.updateChildDictionary(updateType);
            redirectAttributes.addFlashAttribute("message","更新成功");
        }else {
            ChildDictionary queryType = new ChildDictionary();
            queryType.setParentID("123da1");
            queryType.setName(typename);
            List<ChildDictionary> list = childDictionaryService.listChildDictionary(queryType);
            if(list.size()>0&&list!=null){
                redirectAttributes.addFlashAttribute("message","分类名字重复，添加失败~");
                return "redirect:/admin/type";
            }

            ChildDictionary newType = new ChildDictionary();
            newType.setName(typename);
            newType.setParentID("123da1");
            if(!filePath.equals("")){
                newType.setPicture(filePath);
            }else {
                newType.setPicture(Static.typeDefaultPath);
            }

            childDictionaryService.addChildDictionary(newType);
            redirectAttributes.addFlashAttribute("message","添加成功~");
        }


        return "redirect:/admin/type";
    }

}
