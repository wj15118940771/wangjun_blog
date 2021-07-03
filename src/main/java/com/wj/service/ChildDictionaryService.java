package com.wj.service;

import com.wj.pojo.ChildDictionary;
import com.wj.pojo.dto.TypeMsg;

import java.util.List;
import java.util.Map;


public interface ChildDictionaryService {
    void addChildDictionary(ChildDictionary cd);
    void deleteChildDictionary(ChildDictionary cd);
    void updateChildDictionary(ChildDictionary cd);
    List<ChildDictionary> listChildDictionary(ChildDictionary cd);
    List<ChildDictionary> listByparentID(String parentID);
    List<TypeMsg> getMessage(List<ChildDictionary> cdList);
    ChildDictionary getByID(String id);
    List<ChildDictionary> getAllType();
}
