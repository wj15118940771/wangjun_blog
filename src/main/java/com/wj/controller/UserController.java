package com.wj.controller;

import com.wj.pojo.*;
import com.wj.service.BlogService;
import com.wj.service.FollowService;
import com.wj.service.Update_MsgService;
import com.wj.service.UserService;
import com.wj.utils.DataChange;
import com.wj.utils.MD5Utils;
import com.wj.utils.Static;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;
    
    @Autowired
    private FollowService followService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private Update_MsgService update_msgService;

    @GetMapping("")
    public String user(Model model, HttpSession session){
        Object object = session.getAttribute("user");
        User user = (User)object;

        int isLogin = 1;
        if(user!=null) {
            isLogin = 0;
        }
        model.addAttribute("isLogin",isLogin);

        model.addAttribute("user",userService.getUserById(user.getId()));
        model.addAttribute("oneself",1);
        return "user";
    }

    @GetMapping("/{id}/detail")
    public String userdetail(@PathVariable String id, Model model, HttpSession session){
        Object object = session.getAttribute("user");
        User user = (User)object;
        Follow queryFollow = new Follow();
        int oneself = 1;
        int isLogin=0;
        int isFollow=1;
        if(user==null){
            isLogin=1;
        }
        else{
            queryFollow.setUserID(user.getId());
            queryFollow.setFollow(id);
            List<Follow> list = followService.listFollow(queryFollow);
            if(list.size()>0){
                isFollow=0;
            }
            if(id.equals(user.getId()))
                oneself=0;
        }

        Blog queryBlog = new Blog();
        queryBlog.setUserID(id);


        model.addAttribute("isFollow",isFollow);
        model.addAttribute("isLogin",isLogin);
        model.addAttribute("oneself",oneself);
        model.addAttribute("user",userService.getUserById(id));
        model.addAttribute("userBlog",blogService.rightUserBlog(id));
        return "user";
    }

    @PostMapping("/save")
    public String save(User user, Model model,@PathVariable MultipartFile image, HttpSession session, RedirectAttributes attributes){

        Object object = session.getAttribute("user");
        User newuser = (User)object;
        User old = userService.getUserById(newuser.getId());



        if(image!=null&&!image.isEmpty()){
            String userPath= Static.filePath+"/"+user.getId();
            File dir = new File(userPath);
            if(!dir.exists()){
                dir.mkdir();
            }

            String geshi ="."+ DataChange.getFormat( image.getOriginalFilename());

            String imageName = UUID.randomUUID().toString().replace("-","")+geshi;
            String filePath = userPath+"/"+imageName;
            old.setView(Static.userPath+"/"+old.getId()+"/"+imageName);
            try {
                image.transferTo(new File(filePath));

            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }



        old.setName(user.getName());
        old.setEmail(user.getEmail());
        session.setAttribute("user",old);
        userService.updateUser(old);
        attributes.addAttribute("message","更新成功");
        return "redirect:/user/"+old.getId()+"/detail";
    }

    @PostMapping("/change")
    public String updatepassword(String oldPassword ,String newPassword,String new2Password, Model model, HttpSession session, RedirectAttributes attributes){

        Object object = session.getAttribute("user");
        User old = (User)object;

        int isLogin = 1;
        if(old!=null) {
            isLogin = 0;
        }
        model.addAttribute("isLogin",isLogin);

        User update = userService.getUserById(old.getId());
        oldPassword = MD5Utils.code(oldPassword);
        if(!oldPassword.equals(update.getPassword())){
            model.addAttribute("message","旧密码错误~");

        }else {
            update.setPassword(MD5Utils.code(newPassword));
            userService.updateUser(update);
            model.addAttribute("message","密码更新成功~");
        }

        return "password";
    }

    @GetMapping("/password")
    public String changepassword( Model model,HttpSession session, RedirectAttributes attributes){
        Object object = session.getAttribute("user");
        User user = (User)object;

        int isLogin = 1;
        if(user!=null) {
            isLogin = 0;
        }
        model.addAttribute("isLogin",isLogin);
        return "password";
    }

    @PostMapping("/updatemsg")
    public String updatemsg( Model model, HttpSession session, RedirectAttributes attributes){
        Object object = session.getAttribute("user");
        User user = (User)object;
        session.setAttribute("updateMsg","订阅消息+"+update_msgService.countByID(user.getId()));

        return "_fragments :: newmessage";
    }

}
