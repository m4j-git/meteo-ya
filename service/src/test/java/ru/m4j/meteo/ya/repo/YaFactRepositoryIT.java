/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.repo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ya.domain.YaFact;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.srv.config.YaMysqlContainerBase;
import ru.m4j.meteo.ya.srv.config.YaTestDaoConfiguration;

@SpringBootTest(classes = YaTestDaoConfiguration.class)
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class YaFactRepositoryIT extends YaMysqlContainerBase {

    private final Integer geonameId = 1;
    @Autowired
    private YaFactRepository repo;
    @Autowired
    private YaMessageRepository repoM;

    @BeforeEach
    void setUp() {
        assertThat(repo).isNotNull();
        assertThat(repo.count()).isZero();
        assertThat(repoM.count()).isZero();
    }

    @Test
    void testCreateAndFindById(@Qualifier("message_skinny") YaMessage mes, @Autowired YaFact fact) {
        mes = repoM.save(mes);
        mes.addFact(fact);
        fact = repo.save(fact);
        assertThat(repo.count()).isEqualTo(1);
        assertThat(fact.getFactId()).isNotNull();
        YaFact findById = repo.findById(fact.getFactId()).orElseThrow();
        assertThat(fact).isEqualTo(findById);
    }

    @Test
    void testFindFacts(@Qualifier("message") YaMessage mes) {
        mes = repoM.save(mes);
        assertThat(repo.count()).isEqualTo(1);
        final List<YaFact> findFacts = repo.findFacts(geonameId, LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(findFacts.size()).isEqualTo(1);
        assertThat(mes.getFact()).isEqualTo(findFacts.get(0));
    }

    @AfterEach
    void tearDown() {
        repoM.deleteAll();
        assertThat(repo.count()).isZero();
        assertThat(repoM.count()).isZero();
    }

}
