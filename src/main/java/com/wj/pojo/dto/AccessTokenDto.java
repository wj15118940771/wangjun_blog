package com.wj.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenDto {
    private  String grant_type;
    private  String code;
    private  String client_id;
    private  String redirect_uri;
    private  String client_secret;
}
