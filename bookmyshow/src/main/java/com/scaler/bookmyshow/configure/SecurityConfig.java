package com.scaler.bookmyshow.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/login").permitAll() // Allow unrestricted access to /login
                .requestMatchers("/movies").permitAll() // Require authentication for /movies
                .anyRequest().authenticated()
                .and()
                .formLogin().disable();

        return httpSecurity.build();
    }
}
