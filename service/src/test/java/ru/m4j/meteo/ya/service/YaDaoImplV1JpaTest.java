/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
/*
  * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
//@Transactional
class YaDaoImplV1JpaTest extends YaDaoTest {

    YaDaoImplV1JpaTest(ApplicationContext context) {
        dao = (YaDao) context.getBean("yaDaoImplV1Jpa");
    }

}
