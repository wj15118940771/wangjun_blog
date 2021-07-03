package com.wj.service.Impl;


import com.wj.dao.BlogMapper;
import com.wj.dao.ChildDictionaryMapper;
import com.wj.pojo.Blog;
import com.wj.pojo.ChildDictionary;
import com.wj.pojo.dto.TypeMsg;
import com.wj.service.ChildDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChildDictionaryServiceImpl implements ChildDictionaryService {

    @Autowired
    private ChildDictionaryMapper childDictionaryMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public void addChildDictionary(ChildDictionary cd) {

        if(cd.getId()==null||cd.getId()==""){
            cd.setId(UUID.randomUUID().toString().replace("-",""));
        }
        cd.setCreateTime(new Date());
        childDictionaryMapper.addChildDictionary(cd);
    }

    @Override
    public void deleteChildDictionary(ChildDictionary cd) {
        childDictionaryMapper.deleteChildDictionary(cd);
    }

    @Override
    public void updateChildDictionary(ChildDictionary cd) {
        childDictionaryMapper.updateChildDictionary(cd);
    }

    @Override
    public List<ChildDictionary> listChildDictionary(ChildDictionary cd) {
        return childDictionaryMapper.listChildDictionary(cd);
    }

    @Override
    public List<ChildDictionary> listByparentID(String parentID) {
        ChildDictionary cd = new ChildDictionary();
        cd.setParentID(parentID);
        return childDictionaryMapper.listChildDictionary(cd);
    }

    @Override
    public List<TypeMsg> getMessage(List<ChildDictionary> cdList) {
        List<TypeMsg> typeMsgs = new ArrayList<>();
        for(ChildDictionary cd : cdList){
            typeMsgs.add(new TypeMsg(cd,blogMapper.countBlog(cd.getId())));
        }
        return typeMsgs;
    }

    @Override
    public ChildDictionary getByID(String id) {
        return childDictionaryMapper.getByID(id);
    }

    @Override
    public List<ChildDictionary> getAllType() {
        return childDictionaryMapper.getAllType();
    }
}
