/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
/*
  * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@Transactional
class YaDaoImplV1JpaTest extends YaDaoTest {

    @Autowired
    @Qualifier("dao-v1")
    private YaDao dao;

    @Override
    YaDao getDao() {
        return dao;
    }

}
