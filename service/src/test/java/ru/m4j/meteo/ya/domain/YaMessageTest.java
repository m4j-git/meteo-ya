/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ya.srv.config.YaTestBeanFactory;
import ru.m4j.meteo.ya.srv.config.YaTestDomainConfiguration;

@Slf4j
@SpringBootTest(classes = { YaTestDomainConfiguration.class })
@Import(YaTestBeanFactory.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class YaMessageTest {

    @Test
    void entity_test(@Qualifier("message_skinny") YaMessage mes) {
        assertThat(mes).isNotNull();
        assertThat(mes.getMessageUuid()).isNotNull();
        log.info("message" + mes);
    }

    @Test
    void agregate_test(@Qualifier("message") YaMessage mes) {
        assertThat(mes).isNotNull();
        assertThat(mes.getFact()).isNotNull();
        assertThat(mes.getForecast()).isNotNull();
    }

}
