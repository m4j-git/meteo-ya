/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.m4j.meteo.ya.YaTestApplication;

@SpringBootTest(classes = YaTestApplication.class)
class YaPartTest {

    @Test
    public void testFact(@Autowired YaPart part) {
        assertNotNull(part);
        assertThat(part.hashCode()).isEqualTo(0);
        assertThat(part.toString().length()).isGreaterThan(100);
    }


}
