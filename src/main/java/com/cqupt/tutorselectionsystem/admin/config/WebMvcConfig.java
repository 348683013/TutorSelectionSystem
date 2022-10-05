package com.cqupt.tutorselectionsystem.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置磁盘映射成服务器地址
 * Created by zhouzhongzhong on 2022/5/26
 */
@Configuration
@PropertySource({"classpath:fileUpload.properties"})
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${upload.excelFile.resources.locations}")
    private String resourceLocations;

    //把本地磁盘路径映射成服务器路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(resourceLocations);
        WebMvcConfigurer.super.addResourceHandlers(registry);

    }
}
