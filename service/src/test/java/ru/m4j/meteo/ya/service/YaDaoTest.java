/*
  * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

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
import ru.m4j.meteo.ya.domain.YaPart;
import ru.m4j.meteo.ya.repo.YaFactRepository;
import ru.m4j.meteo.ya.repo.YaForecastRepository;
import ru.m4j.meteo.ya.repo.YaMessageRepository;
import ru.m4j.meteo.ya.repo.YaPartRepository;

@SpringBootTest(classes = YaTestApplication.class)
@Transactional
class YaDaoTest {

    private final Integer geonameId = 2;
    @Autowired
    private YaDao dao;
    @Autowired
    private YaFactRepository factRepo;
    @Autowired
    private YaMessageRepository msgRepo;
    @Autowired
    private YaForecastRepository foreRepo;
    @Autowired
    private YaPartRepository partRepo;

    @BeforeEach
    public void setUp() {
        assertThat(dao).isNotNull();
        assertThat(partRepo.count()).isZero();
        assertThat(foreRepo.count()).isZero();
        assertThat(factRepo.count()).isZero();
        assertThat(msgRepo.count()).isZero();
    }

    @Test
    void testCreateMessageSkinny(@Qualifier("message_skinny") YaMessage mes) {
        YaMessage ent = dao.saveMessage(mes, geonameId);
        assertThat(msgRepo.count()).isEqualTo(1);
        assertThat(ent.getGeonameId()).isEqualTo(geonameId);
    }

    @Test
    void testCreateMessage(@Qualifier("message") YaMessage ent) {
        ent = dao.saveMessage(ent, geonameId);
        assertThat(factRepo.count()).isEqualTo(1);
        assertThat(ent.getFact().getFactId()).isNotNull();
        assertThat(foreRepo.count()).isEqualTo(1);
        assertThat(ent.getForecast().getForecastId()).isNotNull();
        assertThat(partRepo.count()).isEqualTo(2);
        YaPart[] parts = new YaPart[ent.getForecast().getParts().size()];
        parts = ent.getForecast().getParts().toArray(parts);
        assertThat(parts).hasSize(2);
        assertThat(parts[0].getPartId()).isNotNull();
        assertThat(parts[0].getForecast()).isNotNull();
        assertThat(parts[0].getForecast().getForecastId()).isNotNull();
    }

    @Test
    void testFindLastMessage(@Qualifier("message") YaMessage mes) {
        YaMessage ent = dao.saveMessage(mes, geonameId);
        final YaMessage ent2 = dao.findLastMessage(geonameId).get();
        assertThat(ent).isEqualTo(ent2);
    }

    @Test
    void testFindFacts(@Qualifier("message") YaMessage mes) {
        YaMessage ent = dao.saveMessage(mes, geonameId);
        List<YaFact> fact2List = dao.findFacts(geonameId, LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(fact2List.size()).isEqualTo(1);
        assertThat(ent.getFact()).isEqualTo(fact2List.get(0));
        assertThat(fact2List.get(0).getFactId()).isNotNull();
    }

    @Test
    void testFindFactsViaSpecification(@Qualifier("message") YaMessage mes) {
        YaMessage ent = dao.saveMessage(mes, geonameId);
        List<YaFact> fact2List = dao.findFactsViaSpecification(geonameId,
            LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(fact2List.size()).isEqualTo(1);
        assertThat(ent.getFact()).isEqualTo(fact2List.get(0));
        assertThat(fact2List.get(0).getFactId()).isNotNull();
    }

    @Test
    void testFindMessages(@Qualifier("message") YaMessage mes) {
        YaMessage ent = dao.saveMessage(mes, geonameId);
        assertThat(msgRepo.count()).isEqualTo(1);
        List<YaMessage> ent2List = dao.findMessages(geonameId, LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(ent2List.size()).isEqualTo(1);
        assertThat(ent).isEqualTo(ent2List.get(0));
    }

    @Test
    void testFindMessagesViaSpecification(@Qualifier("message") YaMessage mes) {
        YaMessage ent = dao.saveMessage(mes, geonameId);
        assertThat(msgRepo.count()).isEqualTo(1);
        List<YaMessage> ent2List = dao.findMessagesViaSpecification(geonameId,
            LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
            LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertThat(ent2List.size()).isEqualTo(1);
        assertThat(ent).isEqualTo(ent2List.get(0));
    }

    @Test
    void testFindMessageByUuid(@Qualifier("message") YaMessage ent1) {
        UUID uuid = UUID.randomUUID();
        ent1.setMessageUuid(uuid);
        ent1 = dao.saveMessage(ent1, geonameId);
        YaMessage ent2 = dao.findMessageByUuid(uuid).get();
        assertThat(ent1).isEqualTo(ent2);
        assertThat(ent1.getMessageUuid()).isEqualTo(ent2.getMessageUuid());
    }

    @AfterEach
    public void tearDown() {
        dao.deleteMessages();
        assertThat(partRepo.count()).isZero();
        assertThat(foreRepo.count()).isZero();
        assertThat(factRepo.count()).isZero();
        assertThat(msgRepo.count()).isZero();
    }

}
