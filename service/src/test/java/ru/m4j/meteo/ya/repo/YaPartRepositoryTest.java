/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ya.YaTestApplication;
import ru.m4j.meteo.ya.domain.YaForecast;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.domain.YaPart;

@SpringBootTest(classes = YaTestApplication.class)
@Transactional
class YaPartRepositoryTest {

    @Autowired
    private YaMessageRepository repoM;
    @Autowired
    private YaForecastRepository repoF;
    @Autowired
    private YaPartRepository repo;

    @BeforeEach
    public void setUp() {
        assertThat(repo).isNotNull();
        assertEquals(0, repo.count());
        assertEquals(0, repoF.count());
        assertEquals(0, repoM.count());
    }

    @Test
    public void testCreateAndFindById(@Qualifier("message_skinny") YaMessage mes, @Qualifier("forecast_skinny") YaForecast fore,
                                      @Autowired YaPart part) {
        mes = repoM.save(mes);
        mes.addForecast(fore);
        fore = repoF.save(fore);
        fore.addPart(part);
        part = repo.save(part);
        assertEquals(1, repo.count());
        assertNotNull(part.getPartId());
        final YaPart findById = repo.findById(part.getPartId()).orElseThrow();
        assertEquals(part, findById);
    }

    @AfterEach
    public void tearDown() {
        repo.deleteAll();
        repoM.deleteAll();
        assertEquals(0, repo.count());
        assertEquals(0, repoF.count());
        assertEquals(0, repoM.count());
    }

}