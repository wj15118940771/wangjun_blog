package com.wj.service.Impl;

import com.wj.dao.TagMapper;
import com.wj.pojo.Tag;
import com.wj.pojo.dto.TagMsg;
import com.wj.service.Blog_TagService;
import com.wj.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private Blog_TagService blog_tagService;

    @Override
    public void addTag(Tag tag) {
        String id = UUID.randomUUID().toString();
        tag.setId(id.replace("-",""));
        tag.setCreateTime(new Date());
        tag.setUpdateTime(new Date());
        tagMapper.addTag(tag);
    }

    @Override
    public void deleteTag(Tag tag) {
        tagMapper.deleteTag(tag);
    }

    @Override
    public void updateTag(Tag tag) {
        tag.setUpdateTime(new Date());
        tagMapper.updateTag(tag);
    }

    @Override
    public List<Tag> listTag(Tag tag) {
        return tagMapper.listTag(tag);
    }

    @Override
    public Tag getByID(String id) {
        return tagMapper.getByID(id);
    }

    @Override
    public List<TagMsg> getMessage(List<Tag> list) {
        List<TagMsg> listMsg = new ArrayList<>();
        for(Tag tag: list){
            listMsg.add(new TagMsg(tag,blog_tagService.countBlog_Tag(tag.getId())));
        }

        return listMsg;
    }


}
