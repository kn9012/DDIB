package com.ddib.notification.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    private final String releaseHostName;

    public CorsMvcConfig(@Value("${releaseHostName}") String releaseHostName) {
        this.releaseHostName = releaseHostName;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .exposedHeaders("Set-Cookie")
                .exposedHeaders("Authorization")
                .allowedOrigins("*")
                .allowedOriginPatterns("*");
    }
}
