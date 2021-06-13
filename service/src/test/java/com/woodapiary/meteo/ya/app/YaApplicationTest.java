/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.app;

import com.woodapiary.meteo.ya.YaTestApplication;
import com.woodapiary.meteo.ya.config.YaConstants;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = YaTestApplication.class)
class YaApplicationTest {

    private static final Logger log = LoggerFactory.getLogger(YaApplicationTest.class);

    @Test
    void testApplication() {
        log.info("test app {}", YaConstants.module);
    }

}
