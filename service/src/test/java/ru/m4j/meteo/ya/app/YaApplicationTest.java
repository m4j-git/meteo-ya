/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ya.YaTestApplication;
import ru.m4j.meteo.ya.config.YaConstants;

@SpringBootTest(classes = YaTestApplication.class)
@Slf4j
class YaApplicationTest {

    @Test
    void testApplication() {
        log.info("test app {}", YaConstants.MODULE);
        assertTrue(true);
    }

}
