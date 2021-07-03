package com.wj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.pojo.Blog;
import com.wj.pojo.Comment;
import com.wj.pojo.Follow;
import com.wj.pojo.User;
import com.wj.pojo.dto.BlogMsg;
import com.wj.pojo.dto.CommentDetail;
import com.wj.pojo.dto.CommentMsg;
import com.wj.pojo.vo.Comments;
import com.wj.service.*;
import com.wj.utils.DataChange;
import com.wj.utils.MarkdownUtils;
import com.wj.utils.Page;
import com.wj.utils.Static;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    public RightService rightService;

    @Autowired
    public BlogService blogService;

    @Autowired
    public UserService userService;

    @Autowired
    public CommentService commentService;

    @Autowired
    public FollowService followService;


    @GetMapping("")
    public String index(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum,
                        HttpSession session, Model model) throws InvocationTargetException, IllegalAccessException {
        Object object = session.getAttribute("user");
        User user = (User)object;

        int isLogin = 1;
        if(user!=null) {
            isLogin = 0;
        }
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
        model.addAttribute("allType",rightService.rightType());
        model.addAttribute("topUser",rightService.rightUser());
        model.addAttribute("topRecommend",rightService.rightBlog());

        return "index";
    }

    @GetMapping("/{id}/detail")
    public String detail(@PathVariable String id, HttpSession session, Model model){
        Object object = session.getAttribute("user");
        User user = (User)object;

        int isFollow =1;
        int isLogin = 1;
        if(user!=null) {
            isLogin = 0;
        }

        Follow queryFollow = new Follow();
        if(user!=null){
            queryFollow.setFollow(id);
            queryFollow.setUserID(user.getId());
            queryFollow.setType("blog");
            List<Follow> listFollow = followService.listFollow(queryFollow);
            if(listFollow.size()>0&&listFollow!=null){
                isFollow=0;
            }
        }


        Blog blog = blogService.getByID(id);
        blog.setVisit(blog.getVisit()+1);
        blogService.updateBlogByVisit(blog);


        blog.setContext(MarkdownUtils.markdownToHtmlExtensions(blog.getContext()));

        User author = userService.getUserById(blog.getUserID());
        model.addAttribute("blogMsg",new BlogMsg(blog,author));

        Comment queryComment = new Comment();
        queryComment.setBlogID(id);
        queryComment.setParentFloor(-1);

        PageHelper.startPage(1, Static.commentParentSum);
        List<Comment> parentComment = commentService.listComment(queryComment);
        PageInfo<Comment> pageInfo =new PageInfo<>(parentComment);


        List<CommentMsg> list = new ArrayList<>();
        for(Comment comment : parentComment){

            User user1 = new User();
            User target = new User();

            Comment queryComment2 = new Comment();
            queryComment2.setBlogID(id);
            queryComment2.setParentFloor(comment.getFloor());

            PageHelper.startPage(1, Static.commentChildSum);
            List<Comment> childComment = commentService.listComment(queryComment2);
            PageInfo<Comment> childPageInfo =new PageInfo<>(childComment);

            List<Comments> child = new ArrayList<>();
            for(Comment comment2: childComment){
                user1 = userService.getUserById(comment2.getUserID());
                target = userService.getUserById(comment2.getTarget());
                Comments childComments = new Comments(comment2,user1,target);
                child.add(childComments);
            }
            user1 = userService.getUserById(comment.getUserID());
            target = userService.getUserById(comment.getTarget());
            Comments parentComments = new Comments(comment,user1,target);

            PageInfo<Comments> childPageInfo1 = new PageInfo<>();
            BeanUtils.copyProperties(childPageInfo,childPageInfo1);
            childPageInfo1.setList(child);

            list.add(new CommentMsg(parentComments,child,childPageInfo1));


        }

        PageInfo<CommentMsg> pageInfo1 = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo,pageInfo1);
        pageInfo1.setList(list);

        model.addAttribute("isFollow",isFollow);
        model.addAttribute("isLogin",isLogin);
        model.addAttribute("allType",rightService.rightType());
        model.addAttribute("topUser",rightService.rightUser());
        model.addAttribute("topRecommend",rightService.rightBlog());
        model.addAttribute("commentCount",commentService.countComment(id));
        model.addAttribute("commentMsg",list);
        model.addAttribute("page",pageInfo1);
        model.addAttribute("tags",DataChange.listTagNameorID(blog.getTagsName()));

        return "blog";
    }


    @PostMapping("/comment")
    public String detail(Comment comment, HttpSession session, Model model) throws IOException {
        Object object = session.getAttribute("user");
        User user = (User)object;
        comment.setUserID(user.getId());

        Comment quertComment = new Comment();
        quertComment.setBlogID(comment.getBlogID());
        quertComment.setParentFloor(comment.getFloor());

        if(comment.getParentFloor()==-1){
            comment.setFloor(commentService.countComment2(quertComment)+1);
        }
        commentService.addComment(comment);
        return "redirect:/index/comment?blogid="+comment.getBlogID()+"&pagenum=1";
    }

    @PostMapping("/comment/commentredirectcomment")
    public String commentredirectcomment(@RequestParam String blogid,@RequestParam int pagenum, HttpSession session, Model model){

        return "redirect:/index/comment?blogid="+blogid+"&pagenum="+pagenum;
    }

    @GetMapping("/comment")
    public String comments(@RequestParam String blogid,@RequestParam int pagenum,  HttpSession session,Model model) {
        Object object = session.getAttribute("user");
        User user = (User)object;


        if(user==null)
            model.addAttribute("isLogin",1);
        else
            model.addAttribute("isLogin",0);

        Blog blog = blogService.getByID(blogid);
        User author = userService.getUserById(blog.getUserID());
        model.addAttribute("blogMsg",new BlogMsg(blog,author));

        Comment queryComment = new Comment();
        queryComment.setBlogID(blogid);
        queryComment.setParentFloor(-1);

        PageHelper.startPage(pagenum, Static.commentParentSum);
        List<Comment> parentComment = commentService.listComment(queryComment);
        PageInfo<Comment> pageInfo =new PageInfo<>(parentComment);

        List<CommentMsg> list = new ArrayList<>();
        for(Comment comment : parentComment){

            User user1 = new User();
            User target = new User();

            Comment queryComment2 = new Comment();
            queryComment2.setBlogID(blogid);
            queryComment2.setParentFloor(comment.getFloor());


            PageHelper.startPage(1, Static.commentChildSum);
            List<Comment> childComment = commentService.listComment(queryComment2);
            PageInfo<Comment> childPageInfo =new PageInfo<>(childComment);

            List<Comments> child = new ArrayList<>();
            for(Comment comment2: childComment){
                user1 = userService.getUserById(comment2.getUserID());
                target = userService.getUserById(comment2.getTarget());
                Comments childComments = new Comments(comment2,user1,target);
                child.add(childComments);
            }
            user1 = userService.getUserById(comment.getUserID());
            target = userService.getUserById(comment.getTarget());
            Comments parentComments = new Comments(comment,user1,target);

            PageInfo<Comments> childPageInfo1 = new PageInfo<>();
            BeanUtils.copyProperties(childPageInfo,childPageInfo1);
            childPageInfo1.setList(child);

            list.add(new CommentMsg(parentComments,child,childPageInfo1));
        }

        PageInfo<CommentMsg> pageInfo1 = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo,pageInfo1);
        pageInfo1.setList(list);
        model.addAttribute("page",pageInfo1);
        model.addAttribute("commentCount",commentService.countComment(blogid));
        model.addAttribute("commentMsg",list);
        model.addAttribute("tags",DataChange.listTagNameorID(blog.getTagsName()));
        return "blog :: commentList";
    }

    @PostMapping("/comment/child")
    public String commentchild(CommentDetail commentDetail, HttpSession session, Model model){

        Comment queryParentComment = commentService.getCommentById(commentDetail.getParentid());

        Object object = session.getAttribute("user");
        User user = (User)object;


        if(user==null)
            model.addAttribute("isLogin",1);
        else
            model.addAttribute("isLogin",0);

        Blog blog = blogService.getByID(queryParentComment.getBlogID());
        User author = userService.getUserById(blog.getUserID());
        model.addAttribute("blogMsg",new BlogMsg(blog,author));

        Comment queryComment = new Comment();
        queryComment.setBlogID(queryParentComment.getBlogID());
        queryComment.setParentFloor(-1);

        PageHelper.startPage(commentDetail.getParentPagenum(), Static.commentParentSum);
        List<Comment> parentComment = commentService.listComment(queryComment);
        PageInfo<Comment> pageInfo =new PageInfo<>(parentComment);

        List<CommentMsg> list = new ArrayList<>();
        for(Comment comment : parentComment){

            User user1 = new User();
            User target = new User();

            Comment queryComment2 = new Comment();
            queryComment2.setBlogID(queryParentComment.getBlogID());
            queryComment2.setParentFloor(comment.getFloor());


            PageHelper.startPage(commentDetail.getChildPagenum(), Static.commentChildSum);
            List<Comment> childComment = commentService.listComment(queryComment2);
            PageInfo<Comment> childPageInfo =new PageInfo<>(childComment);

            List<Comments> child = new ArrayList<>();
            for(Comment comment2: childComment){
                user1 = userService.getUserById(comment2.getUserID());
                target = userService.getUserById(comment2.getTarget());
                Comments childComments = new Comments(comment2,user1,target);
                child.add(childComments);
            }
            user1 = userService.getUserById(comment.getUserID());
            target = userService.getUserById(comment.getTarget());
            Comments parentComments = new Comments(comment,user1,target);

            PageInfo<Comments> childPageInfo1 = new PageInfo<>();
            BeanUtils.copyProperties(childPageInfo,childPageInfo1);
            childPageInfo1.setList(child);

            list.add(new CommentMsg(parentComments,child,childPageInfo1));
        }

        PageInfo<CommentMsg> pageInfo1 = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo,pageInfo1);
        pageInfo1.setList(list);
        model.addAttribute("page",pageInfo1);
        model.addAttribute("commentCount",commentService.countComment(queryParentComment.getBlogID()));
        model.addAttribute("commentMsg",list);
        model.addAttribute("tags",DataChange.listTagNameorID(blog.getTagsName()));
        return "blog :: commentList";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, String query,
    HttpSession session, Model model) {
        Object object = session.getAttribute("user");
        User user = (User)object;

        int isLogin = 1;
        if(user!=null) {
            isLogin = 0;
        }
        PageHelper.startPage(pagenum, Page.pageSize);
        List<Blog> blogList = blogService.queryBlog(query);
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
        model.addAttribute("query",query);

        return "search";
    }

    @PostMapping("/appreciate")
    public String apprecate(String blogid,int appreciate, Model model, HttpSession session, RedirectAttributes attributes){


        Blog queryBlog = new Blog();
        queryBlog = blogService.getByID(blogid);

        queryBlog.setAppreciate(queryBlog.getAppreciate()+1);
        blogService.updateBlogByAppreciate(queryBlog);
        model.addAttribute("blogMsg",new BlogMsg(queryBlog,new User()));
        return "blog :: appreciate";
    }


}
