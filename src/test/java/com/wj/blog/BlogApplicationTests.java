package com.wj.blog;

import com.wj.pojo.*;
import com.wj.service.*;
import com.wj.third.GiteeConfig;
import com.wj.utils.Static;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private MailService mailService;

    @Test
    void contextLoads() {
//        Blog blog = new Blog();
//        blog.setId("123da1");
//        blogService.addBlog(blog);
//        blog.setContext("123456");
//        blogService.updateBlog(blog);
//        blogService.deleteBlog(blog);

//        ChildDictionary childDictionary = new ChildDictionary();
//        childDictionary.setId("123");
//        childDictionaryService.addChildDictionary(childDictionary);
//        childDictionary.setName("123123");
//        childDictionaryService.updateChildDictionary(childDictionary);
//        childDictionaryService.deleteChildDictionary(childDictionary);

//        Comment comment = new Comment();
//        comment.setId("123da1");
//        commentService.addComment(comment);
//        comment.setStatus(3);
//        commentService.updateComment(comment);
//        commentService.deleteComment(comment);

//        ParentDictionary parentDictionary = new ParentDictionary();
//        parentDictionary.setId("123da1");
//        parentDictionaryService.addParentDictionary(parentDictionary);
//        parentDictionary.setStatus(3);
//        parentDictionaryService.updateParentDictionary(parentDictionary);
//        parentDictionaryService.deleteParentDictionary(parentDictionary);

//        Tag tag = new Tag();
//        tag.setId("123da1");
//        tagService.addTag(tag);
//        tag.setStatus(3);
//        tagService.updateTag(tag);
//        tagService.deleteTag(tag);

//        User user = new User();
//        user.setId("123da1");
//        userService.addUser(user);
//        user.setStatus(3);
//        userService.updateUser(user);
//        userService.deleteUser(user);
//        User  user = new User();
//        user.setPassword("123456");
//        user.setName("2392870473@qq.com");
//        List<User> users = userService.listUser(user);
//        System.out.println(users.size());

          List<Blog> list = blogService.queryBlog("靓仔");
       System.out.println("?");
        
    }

    @Test
    void mail() {
        mailService.sendMimeMessge("2392870473@qq.com","123","123");
    }

    @Test
    void applicatin() {
        System.out.println(GiteeConfig.GITEE_CLIENT_ID);
    }

}
