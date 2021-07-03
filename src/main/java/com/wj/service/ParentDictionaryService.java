package com.wj.service;

import com.wj.pojo.ParentDictionary;

import java.util.List;
import java.util.Map;

public interface ParentDictionaryService {
    void addParentDictionary(ParentDictionary pd);
    void deleteParentDictionary(ParentDictionary pd);
    void updateParentDictionary(ParentDictionary pd);
    List<ParentDictionary> listParentDictionary(Map<String,String> pd);
}
