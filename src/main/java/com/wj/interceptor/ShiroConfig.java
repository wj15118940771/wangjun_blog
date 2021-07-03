package com.wj.interceptor;


import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器

        bean.setSecurityManager(defaultWebSecurityManager);

        //添加shiro的内置过滤器
        /* anon：无需认证就可以访问
           authc：必须认证才能访问
           user：必须拥有记住我功能才能用
           perms：拥有对某个资源的权限才能访问
           role：拥有某个角色权限才能访问
         */
        //拦截
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        filterChainDefinitionMap.put("/blog","authc");
        filterChainDefinitionMap.put("/blog/**","authc");
        filterChainDefinitionMap.put("/follow","authc");
        filterChainDefinitionMap.put("/follow/**","authc");
        filterChainDefinitionMap.put("/tag","authc");
        filterChainDefinitionMap.put("/tag/**","authc");
        filterChainDefinitionMap.put("/admin","authc");
        filterChainDefinitionMap.put("/admin/**","authc");
        filterChainDefinitionMap.put("/","anon");


        // filterChainDefinitionMap.put("/user/add","perms[user:add]");

        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //设置登陆请求
        bean.setLoginUrl("/login");
        //未授权页面
        bean.setUnauthorizedUrl("/login");
        return bean;
    }

    @Bean(name="securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }


}
