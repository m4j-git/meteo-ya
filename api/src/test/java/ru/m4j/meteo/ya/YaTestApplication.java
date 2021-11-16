/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@Profile("dev")
@SpringBootApplication
@ComponentScan(basePackages = "ru.m4j.meteo")
public class YaTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(YaTestApplication.class, args);
    }
}
