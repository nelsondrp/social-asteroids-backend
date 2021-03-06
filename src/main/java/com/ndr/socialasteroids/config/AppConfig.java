package com.ndr.socialasteroids.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ndr.socialasteroids.infra.interceptor.LogProcessTimeInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer
{
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new LogProcessTimeInterceptor());
    }
}
