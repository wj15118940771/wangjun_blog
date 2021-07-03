package com.wj.dao;

import com.wj.pojo.ParentDictionary;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface ParentDictionaryMapper {
    void addParentDictionary(ParentDictionary pd);
    void deleteParentDictionary(ParentDictionary pd);
    void updateParentDictionary(ParentDictionary pd);
    List<ParentDictionary> listParentDictionary(Map<String,String> pd);
}
