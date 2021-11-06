/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "meteo.scheduling.enabled", havingValue = "true")
//@Profile("!dev")
public class YaProviderStartupRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(YaProviderStartupRunner.class);

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public void run(String... args) {
        log.info("=== {} startup runner run ===", appName);
    }

}
