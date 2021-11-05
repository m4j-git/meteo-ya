/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import ru.m4j.meteo.aop.LoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class YaLoggingAspectConfiguration {

    @Bean
    @Profile("stage")
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
