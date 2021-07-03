package com.wj.pojo.dto;

import com.wj.pojo.ChildDictionary;
import com.wj.pojo.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagMsg {
    private Tag tag;
    private int count;
}
