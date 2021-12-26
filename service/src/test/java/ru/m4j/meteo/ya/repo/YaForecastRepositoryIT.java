/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.repo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ya.domain.YaForecast;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.srv.config.YaMysqlContainerBase;
import ru.m4j.meteo.ya.srv.config.YaTestDaoConfiguration;

@SpringBootTest(classes = YaTestDaoConfiguration.class)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class YaForecastRepositoryIT extends YaMysqlContainerBase {

    @Autowired
    private YaForecastRepository repo;
    @Autowired
    private YaMessageRepository repoM;

    @BeforeEach
    void setUp() {
        assertThat(repo).isNotNull();
        assertThat(repo.count()).isZero();
    }

    @Test
    void testCreateAndFindById(@Qualifier("message_skinny") YaMessage mes, @Qualifier("forecast_skinny") YaForecast fore) {
        mes = repoM.save(mes);
        mes.addForecast(fore);
        fore = repo.save(fore);
        assertThat(repo.count()).isEqualTo(1);
        assertThat(fore.getForecastId()).isNotNull();
        final YaForecast findById = repo.findById(fore.getForecastId()).orElseThrow();
        assertThat(fore).isEqualTo(findById);
    }

    @AfterEach
    void tearDown() {
        repoM.deleteAll();
        assertThat(repo.count()).isZero();
        assertThat(repoM.count()).isZero();
    }

}
