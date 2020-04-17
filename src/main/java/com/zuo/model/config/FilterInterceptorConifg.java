package com.zuo.model.config;

import com.zuo.model.config.system.MyFillter;
import com.zuo.model.config.system.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 第三方框架的过滤器配置
 */
@Configuration
public class FilterInterceptorConifg implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor);
    }

    @Bean
    public FilterRegistrationBean filter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        MyFillter fillter = new MyFillter();
        registrationBean.setFilter(fillter);
        List<String> urls = new ArrayList<>();
        urls.add("/*");
        registrationBean.setUrlPatterns(urls);
        return registrationBean;
    }


}
