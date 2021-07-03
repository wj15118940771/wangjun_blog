package com.wj.interceptor;

import com.wj.utils.Static;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("配置文件已经生效");
        registry.addResourceHandler("/userImages/**").addResourceLocations("file:"+Static.userStaticResources);
        registry.addResourceHandler("/images/**").addResourceLocations("file:"+Static.typeStaticResources);
    }
}
