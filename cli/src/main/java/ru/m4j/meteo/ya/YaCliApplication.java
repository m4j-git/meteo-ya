/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.service.YaMessageService;

@SpringBootApplication
@ComponentScan(basePackages = { "ru.m4j.meteo.share.app", "ru.m4j.meteo.ya" })
public class YaCliApplication implements CommandLineRunner {

    @Autowired
    YaMessageService service;

    public static void main(final String[] args) {
        new SpringApplicationBuilder(YaCliApplication.class)
            .web(WebApplicationType.NONE)
            .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("hook");
        YaMessageDto dto = service.getLastMessage(1);
        System.out.println(dto);
    }

}
