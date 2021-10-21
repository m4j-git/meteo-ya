/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

import ru.m4j.meteo.ya.YaTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = YaTestApplication.class)
class YaFactTest {

    @Test
    public void testFact(@Autowired YaFact fact) {
        assertNotNull(fact);
        assertThat(fact.hashCode()).isEqualTo(0);
        assertThat(fact.toString().length()).isGreaterThan(100);
    }


}
