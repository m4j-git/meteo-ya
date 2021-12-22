/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ya.config.YaTestDaoConfiguration;
import ru.m4j.meteo.ya.domain.YaForecast;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.domain.YaPart;

@SpringBootTest(classes = YaTestDaoConfiguration.class)
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
        assertThat(repo.count()).isZero();
        assertThat(repoF.count()).isZero();
        assertThat(repoM.count()).isZero();
    }

    @Test
    void testCreateAndFindById(@Qualifier("message_skinny") YaMessage mes, @Qualifier("forecast_skinny") YaForecast fore,
        @Autowired YaPart part) {
        mes = repoM.save(mes);
        mes.addForecast(fore);
        fore = repoF.save(fore);
        fore.addPart(part);
        part = repo.save(part);
        assertThat(repo.count()).isEqualTo(1);
        assertThat(part.getPartId()).isNotNull();
        final YaPart findById = repo.findById(part.getPartId()).orElseThrow();
        assertThat(part).isEqualTo(findById);
    }

    @AfterEach
    public void tearDown() {
        repo.deleteAll();
        repoM.deleteAll();
        assertThat(repo.count()).isZero();
        assertThat(repoF.count()).isZero();
        assertThat(repoM.count()).isZero();
    }

}
