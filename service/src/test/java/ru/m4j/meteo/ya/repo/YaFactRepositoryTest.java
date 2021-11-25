/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ya.YaTestApplication;
import ru.m4j.meteo.ya.domain.YaFact;
import ru.m4j.meteo.ya.domain.YaMessage;

@SpringBootTest(classes = YaTestApplication.class)
@Transactional
class YaFactRepositoryTest {

    private final Integer geonameId = 1;
    @Autowired
    private YaFactRepository repo;
    @Autowired
    private YaMessageRepository repoM;

    @BeforeEach
    public void setUp() {
        assertThat(repo).isNotNull();
        assertThat(0).isEqualTo(repo.count());
        assertThat(0).isEqualTo(repoM.count());
    }

    @Test
    void testCreateAndFindById(@Qualifier("message_skinny") YaMessage mes, @Autowired YaFact fact) {
        mes = repoM.save(mes);
        mes.addFact(fact);
        fact = repo.save(fact);
        assertThat(1).isEqualTo(repo.count());
        assertNotNull(fact.getFactId());
        final YaFact findById = repo.findById(fact.getFactId()).orElseThrow();
        assertThat(fact).isEqualTo(findById);
    }

    @Test
    void testFindFacts(@Qualifier("message") YaMessage mes) {
        mes = repoM.save(mes);
        assertThat(1).isEqualTo(repo.count());
        final List<YaFact> findFacts = repo.findFacts(geonameId, LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(1).isEqualTo(findFacts.size());
        assertThat(mes.getFact()).isEqualTo(findFacts.get(0));
    }

    @AfterEach
    public void tearDown() {
        repoM.deleteAll();
        assertThat(0).isEqualTo(repo.count());
        assertThat(0).isEqualTo(repoM.count());
    }

}
