/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@Profile("!dev")
@SpringBootApplication
@ComponentScan(basePackages = "ru.m4j.meteo")
public class YaApplication extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(YaApplication.class, args);
    }

}
