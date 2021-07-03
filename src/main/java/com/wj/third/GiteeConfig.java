package com.wj.third;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class GiteeConfig {

    public static  String GITEE_CLIENT_ID ;
//    public static final String GITEE_CLIENT_ID = "167bc94ede5f074c81df0b5d16a359f0d5b51a7484f937c5fbed89e7664349d6";
    public static  String GITEE_CLIENT_SECRET ;
//    public static  String GITEE_CLIENT_SECRET = "bcc7b5d809514b40b2288e13dde02a8539f2d9b861f8a6c770aa64beaaca2489";
    public static  String REDIRECT_URI ;
//    public static  String REDIRECT_URI = "http://wangjun.store:8080/callback";
    public static  String grant_type ;
//    public static  String grant_type = "authorization_code";
    public static  String AccesssTokenURL ;
//    public static  String AccesssTokenURL = "https://gitee.com/oauth/token";

    @Value("${gitee.GITEE_CLIENT_ID}")
    public void setGiteeClientId(String giteeClientId) {
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

    @Value("${gitee.grant_type}")
    public  void setGrant_type(String grant_type) {
        GiteeConfig.grant_type = grant_type;
    }

    @Value("${gitee.AccesssTokenURL}")
    public  void setAccesssTokenURL(String accesssTokenURL) {
        AccesssTokenURL = accesssTokenURL;
    }





}
