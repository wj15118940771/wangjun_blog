package com.wj.pojo.vo;

import com.wj.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopUser {
    private User user;
    private int allVisit;
    private int allAppreciate;
}
