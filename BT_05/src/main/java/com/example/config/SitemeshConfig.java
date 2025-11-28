package com.example.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SitemeshConfig {

    @Bean
    // 1. Xóa <ConfigurableSiteMeshFilter> ở dòng này
    public FilterRegistrationBean siteMeshFilter() { 
        
        // 2. Xóa <ConfigurableSiteMeshFilter> ở dòng này luôn
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(); 
        
        filterRegistrationBean.setFilter(new ConfigurableSiteMeshFilter() {
            @Override
            protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
                builder.addDecoratorPath("/*", "/WEB-INF/views/decorators/admin.jsp");
            }
        });
        
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}