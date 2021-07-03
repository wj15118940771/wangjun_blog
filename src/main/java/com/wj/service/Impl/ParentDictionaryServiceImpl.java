package com.wj.service.Impl;

import com.wj.dao.ParentDictionaryMapper;
import com.wj.pojo.ParentDictionary;
import com.wj.service.ParentDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ParentDictionaryServiceImpl implements ParentDictionaryService {

   @Autowired
   private ParentDictionaryMapper parentDictionaryMapper;
    @Override
    public void addParentDictionary(ParentDictionary pd) {
        parentDictionaryMapper.addParentDictionary(pd);
    }

    @Override
    public void deleteParentDictionary(ParentDictionary pd) {
        parentDictionaryMapper.deleteParentDictionary(pd);
    }

    @Override
    public void updateParentDictionary(ParentDictionary pd) {
        parentDictionaryMapper.updateParentDictionary(pd);
    }

    @Override
    public List<ParentDictionary> listParentDictionary(Map<String, String> pd) {
        return null;
    }
}
