package com.scaler.bookmyshow.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/login").allowedOrigins("http://localhost:63342");
        registry.addMapping("/movies").allowedOrigins("http://localhost:63342");
    }
}
