package com.wj.third;

import com.wj.utils.HttpConnection;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class GiteeServiceImpl implements GiteeService {

    @Autowired
    GiteeOauth giteeOauth;


    @Override
    public String authorizeUri() {
        return giteeOauth.authorize();
    }

    @Override
    public String getAccessToken(String code) {
        Map<String ,Object> map = new LinkedHashMap<>();
        map.put("grant_type",GiteeConfig.grant_type);
        map.put("code",code);
        map.put("client_id",GiteeConfig.GITEE_CLIENT_ID);
        map.put("redirect_uri",GiteeConfig.REDIRECT_URI);
        map.put("client_secret",GiteeConfig.GITEE_CLIENT_SECRET);
        System.out.println(map.toString()+"ddd");
        Map<String,Object> resultMap = HttpConnection.sendSmsFormByPost(GiteeConfig.AccesssTokenURL,map);
        return resultMap.get("access_token").toString();
    }

    @Override
    public Map<String,Object> getUserInfo(String accessToken) throws Exception {
        String URL= "https://gitee.com/api/v5/user?access_token="+accessToken;
        Map<String,Object> resultMap = HttpConnection.doGet(URL);
        return resultMap;
    }
}
