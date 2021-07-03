package com.wj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  private String id;
  private String account;
  private String password;
  private String name;
  private String view;
  private String email;
  private Date createTime;
  private int type;
  private int status;
  private String uuid;
}
