/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.app;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import ru.m4j.meteo.ya.YaTestApplication;
import ru.m4j.meteo.ya.config.YaConstants;


@SpringBootTest(classes = YaTestApplication.class)
class YaApplicationTest {

    private static final Logger log = LoggerFactory.getLogger(YaApplicationTest.class);

    @Test
    void testApplication() {
        log.info("test app {}", YaConstants.module);
    }

}
