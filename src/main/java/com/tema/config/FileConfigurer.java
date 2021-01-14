package com.tema.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfigurer implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		System.out.println("配置生效");
        registry.addResourceHandler("/asserts/**").addResourceLocations("file:C:/Users/asus/Desktop/practice/javaweb/telentManageSystem/src/main/resources/static/asserts/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
	}
}
