package com.wj.third;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


public interface GiteeService {
    String authorizeUri();

    String getAccessToken(String code);

    Map<String,Object> getUserInfo(String accessToken) throws Exception;

}
