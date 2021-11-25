/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.repo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        assertEquals(0, repo.count());
        assertEquals(0, repoM.count());
    }

    @Test
    void testCreateAndFindById(@Qualifier("message_skinny") YaMessage mes, @Autowired YaFact fact) {
        mes = repoM.save(mes);
        mes.addFact(fact);
        fact = repo.save(fact);
        assertEquals(1, repo.count());
        assertNotNull(fact.getFactId());
        final YaFact findById = repo.findById(fact.getFactId()).orElseThrow();
        assertEquals(fact, findById);
    }

    @Test
    void testFindFacts(@Qualifier("message") YaMessage mes) {
        mes = repoM.save(mes);
        assertEquals(1, repo.count());
        final List<YaFact> findFacts = repo.findFacts(geonameId, LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertEquals(1, findFacts.size());
        assertEquals(mes.getFact(), findFacts.get(0));
    }

    @AfterEach
    public void tearDown() {
        repoM.deleteAll();
        assertEquals(0, repo.count());
        assertEquals(0, repoM.count());
    }

}
