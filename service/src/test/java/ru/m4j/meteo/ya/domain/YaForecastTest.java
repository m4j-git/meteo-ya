/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ya.config.YaTestBeanFactory;
import ru.m4j.meteo.ya.config.YaTestDomainConfiguration;

@Slf4j
@SpringBootTest(classes = { YaTestDomainConfiguration.class })
@Import(YaTestBeanFactory.class)
class YaForecastTest {

    @Test
    void entity_test(@Qualifier("forecast") YaForecast fore) {
        assertThat(fore).isNotNull().isNotNull();
        assertThat(fore.getParts()).isNotNull();
        log.info("forecast: " + fore);
    }

}
