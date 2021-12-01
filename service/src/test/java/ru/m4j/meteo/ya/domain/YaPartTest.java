/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.m4j.meteo.ya.YaTestApplication;

@SpringBootTest(classes = YaTestApplication.class)
class YaPartTest {

    @Test
    void entity_test(@Autowired YaPart part) {
        assertThat(part).isNotNull();
        assertThat(part.hashCode()).isZero();
        assertThat(part.toString()).isNotEmpty();
    }

}
