package com.wj.pojo.vo;

import com.wj.pojo.Blog;
import com.wj.pojo.Update_Msg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMsg {
    private Update_Msg updateMsg;
    private Blog blog;
}
