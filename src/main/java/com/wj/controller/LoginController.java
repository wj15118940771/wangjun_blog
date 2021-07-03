package com.wj.controller;

import com.alibaba.fastjson.JSONObject;
import com.wj.pojo.User;
import com.wj.pojo.dto.AccessTokenDto;
import com.wj.service.MailService;
import com.wj.service.UserService;
import com.wj.third.GiteeConfig;
import com.wj.third.GiteeService;
import com.wj.utils.HttpConnection;
import com.wj.utils.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.*;

@Controller
@CrossOrigin(origins = "*",maxAge = 3600)
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private GiteeService giteeService;

    @GetMapping("/resetpw")
    public String getReset(){
        return "resetpw";
    }

    @GetMapping("/register")
    public String getRegister(){
        return "register";
    }

    @GetMapping("/login")
    public String getLogin(Model model,RedirectAttributes attributes){
        model.addAttribute("message",model.getAttribute("message"));
        model.addAttribute("gitee",giteeService.authorizeUri());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String account,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        session.setAttribute("updateMsg","订阅消息+3");

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account , MD5Utils.code(password));
        try {
            subject.login(token);
            User user = userService.getUserByAccount(account);
            user.setPassword("null");
            session.setAttribute("user",user);
            return "redirect:/index";
        }catch (UnknownAccountException e){
            attributes.addFlashAttribute("message", "用户名错误");
            return "redirect:/login";
        }catch (IncorrectCredentialsException e){
            attributes.addFlashAttribute("message", "密码错误");
            return "redirect:/login";
        }

    }

    @PostMapping("/register")
    public String register( User newuser,HttpServletRequest request,
                            HttpSession session,
                            RedirectAttributes attributes){
          User queryUser =new User();
          queryUser.setAccount(newuser.getAccount());
          List<User> list =userService.listUser(queryUser);
        User queryUser1 =new User();
        queryUser1.setEmail(newuser.getEmail());
        List<User> list1 =userService.listUser(queryUser);
          if(list1!=null&&list1.size()>0){
              attributes.addFlashAttribute("message", "注册失败！邮箱已注册");
              return "redirect:/register";
          }

        if(list==null||list.size()==0) {
            User user = new User();
            user.setStatus(1);
            user.setAccount(request.getParameter("account"));
            user.setView("/images/123.jpg");
            user.setPassword(MD5Utils.code(request.getParameter("password")));
            user.setName(request.getParameter("name"));
            user.setEmail(request.getParameter("email"));
            String uuid = UUID.randomUUID().toString().replace("-","");
            user.setId(uuid);
            userService.addUser(user);
            mailService.sendMimeMessge(user.getEmail(),"邮箱注册",uuid );
            attributes.addFlashAttribute("message", "注册链接已经发送至邮箱！请及时查看");
            return "redirect:/login";
        }else {
            attributes.addFlashAttribute("message", "注册失败！账号已存在");
            return "redirect:/register";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session,RedirectAttributes attributes){
        session.removeAttribute("user");
        attributes.addFlashAttribute("message", "退出登陆~");
        return "redirect:/index";
    }

    @GetMapping("/email/active")
    public String active(@RequestParam String code, Model model){
        User user = userService.getUserById(code);
        if(user==null){
            model.addAttribute("message","注册失败~");
            return "register";
        }else {
            Date registerTime = user.getCreateTime();
            Date currentTime = new Date();
            long diff = currentTime.getTime()-registerTime.getTime();
            if(diff>300000){
                model.addAttribute("message","验证码已过期~");
                return "register";
            }else {
                user.setStatus(0);
                userService.updateUser(user);
                model.addAttribute("message","注册成功请登陆~");
                return "login";
            }
        }

    }

    @GetMapping("/callback")
    public String callback(@RequestParam String code,HttpSession session,RedirectAttributes attributes,Model model) throws Exception {

        try {
            String access_token = giteeService.getAccessToken(code);
            Map<String,Object> map = giteeService.getUserInfo(access_token);

            User queryUser =userService.getUserByAccountUseThird(map.get("login").toString());

            Subject subject = SecurityUtils.getSubject();

            if(queryUser!=null){
                User user = queryUser;
                UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount() ,user.getPassword());
                subject.login(token);
                user.setPassword("null");
                session.setAttribute("user",user);
            }else {
                User user = new User();
                user.setAccount(map.get("login").toString());
                user.setPassword(MD5Utils.code("123456"));
                user.setName(map.get("name").toString());
                user.setView(map.get("avatar_url").toString());
                if(map.get("email")!=null){
                    user.setEmail(map.get("email").toString());
                }else {
                    user.setEmail("");
                }


                userService.addUser(user);
                UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount() ,MD5Utils.code(user.getPassword()));
                subject.login(token);
                user.setPassword("null");
                session.setAttribute("user",userService.getUserByAccount(map.get("login").toString()));
            }

            return "redirect:/index";
        }catch (UnknownAccountException e){
            attributes.addFlashAttribute("message", "用户名错误");
            return "redirect:/login";
        }catch (IncorrectCredentialsException e){
            attributes.addFlashAttribute("message", "密码错误");
            return "redirect:/login";
        }

    }

    @PostMapping("/resetpw")
    public String reset( String account,
                        HttpSession session,
                        RedirectAttributes attributes,Model model){

        User user = userService.getUserByAccount(account);
        if(user==null){
            model.addAttribute("message","账号不存在~");
            return "resetpw";
        }
        if(user.getEmail()==null||user.getEmail().equals("")){
            model.addAttribute("message","该账户未绑定邮箱~");
            return "resetpw";
        }else {
            String uuid = UUID.randomUUID().toString().replace("-","");
            user.setUuid(uuid);
            userService.updateUser(user);
            mailService.sendResetpwMessge(user.getEmail(),"重置密码邮件",uuid);
            model.addAttribute("message","重置信息已经发到邮箱，请注意查收~");
            return "resetpw";
        }



    }

    @GetMapping("/resetpw/active")
    public String resetactive(@RequestParam String code, Model model,RedirectAttributes attributes){

        User queryUser = new User();
        queryUser.setUuid(code);
        List<User> list = userService.listUser(queryUser);
        if(list!=null&&list.size()>0){
            User user = list.get(0);
            user.setUuid("email");
            user.setPassword(MD5Utils.code("123456"));
            userService.updateUser(user);
            attributes.addFlashAttribute("message","密码重置成功，重置密码为123456。");
            return "redirect:/login";
        }else {
            attributes.addFlashAttribute("message","链接失效");
            return "redirect:/login";
        }


    }
}
