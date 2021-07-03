package com.wj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.pojo.*;
import com.wj.pojo.dto.BlogMsg;
import com.wj.pojo.dto.FollowMsgBlog;
import com.wj.pojo.dto.FollowMsgUser;
import com.wj.service.*;
import com.wj.utils.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    public FollowService followService;

    @Autowired
    public UserService userService;

    @Autowired
    public BlogService blogService;

    @Autowired
    private RightService rightService;

    @Autowired
    private Update_MsgService update_msgService;

    @GetMapping("/user")
    public String myfollowUser(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, HttpSession session, Model model){
        Object object = session.getAttribute("user");
        User user = (User)object;
        if(user==null)
            model.addAttribute("isLogin",1);
        else
            model.addAttribute("isLogin",0);
        Follow queryFollow = new Follow();
        queryFollow.setUserID(user.getId());
        queryFollow.setType("user");
        PageHelper.startPage(pagenum, Page.pageSize);
        List<Follow> list = followService.listFollow(queryFollow);
        PageInfo<Follow> pageInfo = new PageInfo<>(list);

        List<FollowMsgUser> listMsg = new ArrayList<>();
        for(Follow follow:list){
            FollowMsgUser fmu = new FollowMsgUser();
            fmu.setUser(userService.getUserById(follow.getFollow()));
            fmu.setFollow(follow);
            fmu.setUpdateMsgs(rightService.updateMsgs(user.getId(),follow.getFollow(),1));
            listMsg.add(fmu);
        }

        //在这里吧信息存到新的去
        PageInfo<FollowMsgUser> pageInfo1 = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo,pageInfo1);
        pageInfo1.setList(listMsg);
        model.addAttribute("message",model.getAttribute("message"));
        model.addAttribute("page",pageInfo1);
        model.addAttribute("page1",rightService.updateMsgs("","",1));
        return "follow_user";
    }

    @GetMapping("/{id}/deleteuser")
    public String deleteUser(@PathVariable String id, Model model, HttpSession session, RedirectAttributes attributes){
        Follow follow = new Follow();
        follow.setId(id);
        followService.deleteFollow(follow);
        attributes.addFlashAttribute("message","取消关注成功");
        return "redirect:/follow/user";
    }

    @GetMapping("/blog")
    public String myfollowBlog(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, HttpSession session, Model model){
        Object object = session.getAttribute("user");
        User user = (User)object;
        if(user==null)
            model.addAttribute("isLogin",1);
        else
            model.addAttribute("isLogin",0);
        Follow queryFollow = new Follow();
        queryFollow.setUserID(user.getId());
        queryFollow.setType("blog");
        PageHelper.startPage(pagenum, Page.pageSize);
        List<Follow> list = followService.listFollowEXblogStatus(queryFollow);
        PageInfo<Follow> pageInfo = new PageInfo<>(list);

        List<FollowMsgBlog> listMsg = new ArrayList<>();
        for(Follow follow:list){
            listMsg.add(new FollowMsgBlog(blogService.getByID(follow.getFollow()),follow));
        }

        //在这里吧信息存到新的去
        PageInfo<FollowMsgBlog> pageInfo1 = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo,pageInfo1);
        pageInfo1.setList(listMsg);

        model.addAttribute("page",pageInfo1);
        return "follow_blog";
    }

    @GetMapping("/{id}/deleteblog")
    public String deleteBlog(@PathVariable String id, Model model, HttpSession session, RedirectAttributes attributes){
        Follow follow = new Follow();
        follow.setId(id);
        followService.deleteFollow(follow);
        attributes.addFlashAttribute("message","取消关注成功");
        return "redirect:/follow/blog";
    }

    @PostMapping("/userfollow")
    public String userfollow(String id, HttpSession session, Model model){
        Object object = session.getAttribute("user");
        User user = (User)object;
        Follow newFollow = new Follow();
        newFollow.setUserID(user.getId());
        newFollow.setFollow(id);
        newFollow.setType("user");

        if(followService.listFollow(newFollow).size()<1){
            followService.addFollow(newFollow);
        }

        model.addAttribute("isFollow",0);
        model.addAttribute("user",userService.getUserById(id));
        return "user :: userfollow";

    }

    @PostMapping("/userunfollow")
    public String userunfollow(String id, HttpSession session, Model model){
        Object object = session.getAttribute("user");
        User user = (User)object;
        Follow queryFollow = new Follow();
        queryFollow.setUserID(user.getId());
        queryFollow.setFollow(id);
        queryFollow.setType("user");
        Follow deleteFollow = followService.listFollow(queryFollow).get(0);
        followService.deleteFollow(deleteFollow);
        model.addAttribute("isFollow",1);
        model.addAttribute("user",userService.getUserById(id));
        return "user :: userfollow";

    }

    @PostMapping("/blogfollow")
    public String blogfollow(String id, HttpSession session, Model model){
        Object object = session.getAttribute("user");
        User user = (User)object;
        Follow newFollow = new Follow();
        newFollow.setUserID(user.getId());
        newFollow.setFollow(id);
        newFollow.setType("blog");

        if(followService.listFollow(newFollow).size()<1){
            followService.addFollow(newFollow);
        }
        Blog blog1 = blogService.getByID(id);
        model.addAttribute("blogMsg",new BlogMsg(blog1,new User()));
        model.addAttribute("isFollow",0);
        model.addAttribute("user",userService.getUserById(id));
        return "blog :: blogfollow";

    }

    @PostMapping("/blogunfollow")
    public String blogunfollow(String id, HttpSession session, Model model){
        Object object = session.getAttribute("user");
        User user = (User)object;
        Follow queryFollow = new Follow();
        queryFollow.setUserID(user.getId());
        queryFollow.setFollow(id);
        queryFollow.setType("blog");
        Follow deleteFollow = followService.listFollow(queryFollow).get(0);
        followService.deleteFollow(deleteFollow);

        Blog blog1 = blogService.getByID(id);
        model.addAttribute("blogMsg",new BlogMsg(blog1,new User()));
        model.addAttribute("isFollow",1);
        model.addAttribute("user",userService.getUserById(id));
        return "blog :: blogfollow";

    }

    @PostMapping("/user/updateMsg")
    public String updateMsg( String userid,int pagenum,HttpSession session, Model model){
        Object object = session.getAttribute("user");
        User user = (User)object;
        String follower = user.getId();
        model.addAttribute("page1",rightService.updateMsgs(follower,userid,pagenum));
        return "follow_user :: updateMsg";
    }

    @PostMapping("/user/updateMsg/done")
    public String updateMsgDone(String updateid,String userid,HttpSession session, Model model){
        Object object = session.getAttribute("user");
        User user = (User)object;
        String follower = user.getId();

        if(updateid.equals("0")) {
            Update_Msg um = new Update_Msg();
            um.setFollower(follower);
            um.setUserID(userid);
            update_msgService.deleteWhenUnfollow(um);
        }else {
            Update_Msg query = update_msgService.getByID(updateid);
            query.setStatus(1);
            update_msgService.updateUpdate_Msg(query);
        }

        model.addAttribute("page1",rightService.updateMsgs(follower,userid,1));
        return "follow_user :: updateMsg";

    }

    @GetMapping("/user/updateMsg/done")
    public String updateMsgDoned(String updateid,String blogid,HttpSession session, Model model){
        Object object = session.getAttribute("user");
        User user = (User)object;

        Update_Msg query = update_msgService.getByID(updateid);

        query.setStatus(1);
        update_msgService.updateUpdate_Msg(query);

        return "redirect:/index/" +blogid+
                "/detail";

    }

    @GetMapping("/user/updateMsg/clearall")
    public String updateMsgclearall(HttpSession session, Model model,RedirectAttributes attributes){
        Object object = session.getAttribute("user");
        User user = (User)object;

        Update_Msg um = new Update_Msg();
        um.setFollower(user.getId());
        update_msgService.deleteAllByFollower(um);
        attributes.addFlashAttribute("message","已清空订阅更新消息");
        return "redirect:/follow/user";

    }
}
