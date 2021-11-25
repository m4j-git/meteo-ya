/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.repo;

import static org.assertj.core.api.Assertions.assertThat;
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

@SpringBootTest(classes = YaTestApplication.class)
@Transactional
class YaForecastRepositoryTest {

    @Autowired
    private YaForecastRepository repo;
    @Autowired
    private YaMessageRepository repoM;

    @BeforeEach
    public void setUp() {
        assertThat(repo).isNotNull();
        assertThat(repo.count()).isZero();
    }

    @Test
    void testCreateAndFindById(@Qualifier("message_skinny") YaMessage mes, @Qualifier("forecast_skinny") YaForecast fore) {
        mes = repoM.save(mes);
        mes.addForecast(fore);
        fore = repo.save(fore);
        assertThat(repo.count()).isEqualTo(1);
        assertNotNull(fore.getForecastId());
        final YaForecast findById = repo.findById(fore.getForecastId()).orElseThrow();
        assertThat(fore).isEqualTo(findById);
    }

    @AfterEach
    public void tearDown() {
        repoM.deleteAll();
        assertThat(repo.count()).isZero();
        assertThat(repoM.count()).isZero();
    }

}
