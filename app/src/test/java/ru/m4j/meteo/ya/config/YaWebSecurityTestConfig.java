/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class YaWebSecurityTestConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().anyRequest().permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
