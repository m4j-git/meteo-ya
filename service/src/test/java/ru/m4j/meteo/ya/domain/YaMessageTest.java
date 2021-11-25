/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import ru.m4j.meteo.ya.YaTestApplication;

@SpringBootTest(classes = YaTestApplication.class)
class YaMessageTest {

    @Test
    void testMessageSkinny(@Qualifier("message_skinny") YaMessage mes) {
        assertThat(mes).isNotNull();
        assertThat(mes.getMessageUuid()).isNotNull();
        assertThat(mes.hashCode()).isZero();
        assertThat(mes.toString().length()).isGreaterThan(100);
    }

    @Test
    void testMessage(@Qualifier("message") YaMessage mes) {
        assertThat(mes).isNotNull();
        assertThat(mes.getFact()).isNotNull();
        assertThat(mes.getForecast()).isNotNull();
    }

}
