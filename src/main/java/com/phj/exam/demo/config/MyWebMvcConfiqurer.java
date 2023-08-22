package com.phj.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.phj.exam.demo.interceptor.BeforeActionInterceptor;
import com.phj.exam.demo.interceptor.NeedLoginInterceptor;
@Configuration
public class MyWebMvcConfiqurer implements WebMvcConfigurer {
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;
	@Autowired
	NeedLoginInterceptor needLoginInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/error")
				.excludePathPatterns("/resource/**");
		
		registry.addInterceptor(needLoginInterceptor)
		.addPathPatterns("/user/article/write")
		.addPathPatterns("/user/article/doWrite")
		.addPathPatterns("/user/article/modify")
		.addPathPatterns("/user/article/doModify")
		.addPathPatterns("/user/article/doDelete");
	}
}
