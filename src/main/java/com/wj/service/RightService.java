package com.wj.service;
import com.github.pagehelper.PageInfo;
import com.wj.pojo.Blog;
import com.wj.pojo.dto.TypeMsg;
import com.wj.pojo.vo.TopUser;
import com.wj.pojo.vo.UpdateMsg;

import java.util.List;

public interface RightService {

    List<TypeMsg> rightType();
    List<TopUser> rightUser();
    List<Blog> rightBlog();
    PageInfo<UpdateMsg> updateMsgs(String follower,String userid,int pagenum);
}
