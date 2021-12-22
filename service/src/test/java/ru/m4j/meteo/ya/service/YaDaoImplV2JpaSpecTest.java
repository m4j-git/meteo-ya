/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
/*
  * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import ru.m4j.meteo.ya.config.YaTestDaoConfiguration;

@SpringBootTest(classes = YaTestDaoConfiguration.class)
class YaDaoImplV2JpaSpecTest extends YaDaoTest {

    YaDaoImplV2JpaSpecTest(ApplicationContext context) {
        dao = (YaDao) context.getBean("yaDaoImplV2JpaSpec");
    }

}