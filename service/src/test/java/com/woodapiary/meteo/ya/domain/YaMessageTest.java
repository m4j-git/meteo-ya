/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.domain;

import com.woodapiary.meteo.ya.YaTestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = YaTestApplication.class)
class YaMessageTest {


    @Test
    public void testMessageSkinny(@Qualifier("message_skinny") YaMessage mes) {
        assertNotNull(mes);
        assertNotNull(mes.getMessageUuid());
        assertThat(mes.hashCode()).isEqualTo(0);
        assertThat(mes.toString().length()).isGreaterThan(100);
    }

    @Test
    public void testMessage(@Qualifier("message") YaMessage mes) {
        assertNotNull(mes);
        assertNotNull(mes.getFact());
        assertNotNull(mes.getForecast());
    }


}
