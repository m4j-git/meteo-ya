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
class YaFactTest {

    @Test
    void entity_test(@Autowired YaFact fact) {
        assertThat(fact).isNotNull();
        assertThat(fact.hashCode()).isZero();
        assertThat(fact.toString()).isNotEmpty();
    }

}
