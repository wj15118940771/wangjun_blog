package com.wj.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wj.pojo.*;
import com.wj.pojo.dto.FollowMsgUser;
import com.wj.pojo.dto.TypeMsg;
import com.wj.pojo.vo.TopUser;
import com.wj.pojo.vo.UpdateMsg;
import com.wj.service.*;
import com.wj.utils.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RightServiceImpl implements RightService {

    @Autowired
    public ChildDictionaryService childDictionaryService;

    @Autowired
    public UserService userService;

    @Autowired
    public BlogService blogService;

    @Autowired
    public Update_MsgService update_msgService;

    @Override
    public List<TypeMsg> rightType() {

        List<TypeMsg> list = childDictionaryService.getMessage(childDictionaryService.listByparentID("123da1"));
        list.sort((o1, o2) -> o1.compareTo(o2));
        if(list.size()>9){
            list.subList(0,8);
        }
        return list;
    }

    @Override
    public List<TopUser> rightUser() {
        List<TopUser> list= new ArrayList<>();
        List<User> users = userService.sumUser();
        for(User user: users){

            list.add(new TopUser(user,
                    blogService.countVisit(user.getId()),
                    blogService.countAppreciate(user.getId())));
        }
         return list;
    }

    @Override
    public List<Blog> rightBlog() {
         return blogService.topBlog();
    }

    @Override
    public PageInfo<UpdateMsg> updateMsgs(String follower, String userid,int pagenum) {
        List<UpdateMsg> result = new ArrayList<>();

        PageHelper.startPage(pagenum, Page.pageSize);
        Update_Msg queryUM = new Update_Msg();
        queryUM.setUserID(userid);
        queryUM.setFollower(follower);
        List<Update_Msg> queryList = update_msgService.listUpdate_Msg(queryUM);
        PageInfo<Update_Msg> pageInfo = new PageInfo<>(queryList);

        for(Update_Msg um : queryList){
            Blog blog = blogService.getByID(um.getBlogID());
            result.add(new UpdateMsg(um,blog));
        }

        PageInfo<UpdateMsg> pageInfo1 = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo,pageInfo1);
        pageInfo1.setList(result);

        return pageInfo1;
    }
}
