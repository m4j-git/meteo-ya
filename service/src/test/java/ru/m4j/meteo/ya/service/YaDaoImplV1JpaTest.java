/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
/*
  * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ya.srv.config.YaTestDaoConfiguration;

@SpringBootTest(classes = YaTestDaoConfiguration.class)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class YaDaoImplV1JpaTest extends YaDaoTest {

    YaDaoImplV1JpaTest(ApplicationContext context) {
        super((YaDao) context.getBean("yaDaoImplV1Jpa"));
    }

}
