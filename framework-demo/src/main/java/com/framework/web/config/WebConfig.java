package com.framework.web.config;


import com.framework.interceptor.TimeInterceptor;
import com.framework.web.filter.TimeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 手动配置类
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport{

    @Autowired
    private TimeInterceptor timeInterceptor;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns 用于添加拦截规则 
        // excludePathPatterns 用户排除拦截 
//        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatternss("/toLogin","/login","/assets/**","/js/**");
        registry.addInterceptor(timeInterceptor);
    }

    /**
     * 手动配置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean timeFilter(){
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        TimeFilter timeFilter=new TimeFilter();
        registrationBean.setFilter(timeFilter);
        List<String> urls = new ArrayList<>();
        // 设置拦截url
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);

        return registrationBean;
    }

    /**
     * 设置异步请求配置
     * @param configurer
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        //super.configureAsyncSupport(configurer);
    }
}
