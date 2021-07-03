package com.wj.dao;

import com.wj.pojo.ChildDictionary;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ChildDictionaryMapper {
    void addChildDictionary(ChildDictionary cd);
    void deleteChildDictionary(ChildDictionary cd);
    void updateChildDictionary(ChildDictionary cd);
    List<ChildDictionary> listChildDictionary(ChildDictionary cd);
    ChildDictionary getByID(String id);
    List<ChildDictionary> getAllType();
}
