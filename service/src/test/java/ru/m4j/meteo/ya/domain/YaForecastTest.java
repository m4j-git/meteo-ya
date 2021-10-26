/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import ru.m4j.meteo.ya.YaTestApplication;

@SpringBootTest(classes = YaTestApplication.class)
class YaForecastTest {

    @Test
    public void testForecast(@Qualifier("forecast") YaForecast fore) {
        assertNotNull(fore);
        assertNotNull(fore.getParts());
        assertThat(fore.hashCode()).isEqualTo(0);
        assertThat(fore.toString().length()).isGreaterThan(100);
    }


}
