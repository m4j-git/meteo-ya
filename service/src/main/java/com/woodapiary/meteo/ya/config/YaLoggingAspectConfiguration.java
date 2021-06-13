/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.config;

import com.woodapiary.meteo.ya.aop.YaLoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class YaLoggingAspectConfiguration {

    @Bean
    @Profile("dev")
    public YaLoggingAspect loggingAspect(Environment env) {
        return new YaLoggingAspect(env);
    }
}
