package com.wj.dao;

import com.wj.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface TagMapper {
    void addTag(Tag tag);
    void deleteTag(Tag tag);
    void updateTag(Tag tag);
    List<Tag> listTag(Tag tag);
    Tag getByID(String id);
}
