/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;

@Profile("!dev")
@SpringBootApplication
@ComponentScan(basePackages = "com.woodapiary.meteo")
public class YaApplication extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(YaApplication.class);

    public static void main(final String[] args) {
        SpringApplication.run(YaApplication.class, args);
    }

}
