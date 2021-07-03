package com.wj.third;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GiteeOauth implements BaseOauth{
    private static  String GITEE_CLIENT_ID ;
    private static  String GITEE_CLIENT_SECRET ;
    private static  String REDIRECT_URI ;

    @Value("${gitee.GITEE_CLIENT_ID}")
    public  void setGiteeClientId(String giteeClientId) {
        GITEE_CLIENT_ID = giteeClientId;
    }

    @Value("${gitee.GITEE_CLIENT_SECRET}")
    public  void setGiteeClientSecret(String giteeClientSecret) {
        GITEE_CLIENT_SECRET = giteeClientSecret;
    }

    @Value("${gitee.REDIRECT_URI}")
    public  void setRedirectUri(String redirectUri) {
        REDIRECT_URI = redirectUri;
    }

    @Override
    public String authorize() {
        return "https://gitee.com/oauth/authorize?client_id=" + GITEE_CLIENT_ID + "&response_type=code&redirect_uri=" + REDIRECT_URI+"&response_type=code";
    }

    @Override
    public String accessToken(String code) {
        return "https://gitee.com/oauth/token?grant_type=authorization_code&code=" + code + "&client_id=" + GITEE_CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&client_secret=" + GITEE_CLIENT_SECRET;
    }


    @Override
    public String userInfo(String accessToken) {
        return "https://gitee.com/api/v5/user?access_token=" + accessToken;
    }
}
