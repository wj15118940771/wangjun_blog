package com.wj.service;

import com.wj.pojo.ChildDictionary;
import com.wj.pojo.Tag;
import com.wj.pojo.dto.TagMsg;

import java.util.List;
import java.util.Map;

public interface TagService {
    void addTag(Tag tag);
    void deleteTag(Tag tag);
    void updateTag(Tag tag);
    List<Tag> listTag(Tag tag);
    Tag getByID(String id);
    List<TagMsg> getMessage(List<Tag> list);
}
