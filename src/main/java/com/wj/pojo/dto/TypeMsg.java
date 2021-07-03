package com.wj.pojo.dto;

import com.wj.pojo.ChildDictionary;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeMsg implements Comparable<TypeMsg>{
    private ChildDictionary type;
    private int count;

    @Override
    public int compareTo(TypeMsg o) {
        if(this.count>o.getCount())
        return 1;
        else return -1;
    }
}
