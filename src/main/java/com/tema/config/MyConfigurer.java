package com.tema.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tema.intercepors.LoginInterceptor;

@Configuration
public class MyConfigurer implements WebMvcConfigurer{
	@Autowired
    private LoginInterceptor loginInterceptor;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//add view controllers and set the name of the view
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/index.html").setViewName("index");
		registry.addViewController("/signup.html").setViewName("signup");
		registry.addViewController("/submitCaptcha.html").setViewName("submitCaptcha");
	}
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/", "/index", "/signup", "/signup/**", "/submitCaptcha" , "/asserts/**");
    }
	
}
