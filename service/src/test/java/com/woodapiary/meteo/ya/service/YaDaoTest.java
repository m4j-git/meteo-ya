/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.service;

import com.woodapiary.meteo.ya.YaTestApplication;
import com.woodapiary.meteo.ya.domain.YaFact;
import com.woodapiary.meteo.ya.domain.YaMessage;
import com.woodapiary.meteo.ya.domain.YaPart;
import com.woodapiary.meteo.ya.repo.YaFactRepository;
import com.woodapiary.meteo.ya.repo.YaForecastRepository;
import com.woodapiary.meteo.ya.repo.YaMessageRepository;
import com.woodapiary.meteo.ya.repo.YaPartRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        assertEquals(0, partRepo.count());
        assertEquals(0, foreRepo.count());
        assertEquals(0, factRepo.count());
        assertEquals(0, msgRepo.count());
    }

    @Test
    public void testCreateMessageSkinny(@Qualifier("message_skinny") YaMessage mes) {
        YaMessage ent = dao.saveMessage(mes, geonameId);
        assertEquals(1, msgRepo.count());
        assertEquals(geonameId, ent.getGeonameId());
    }

    @Test
    public void testCreateMessage(@Qualifier("message") YaMessage ent) {
        ent = dao.saveMessage(ent, geonameId);
        assertEquals(1, factRepo.count());
        assertNotNull(ent.getFact().getFactId());
        assertEquals(1, foreRepo.count());
        assertNotNull(ent.getForecast().getForecastId());
        assertEquals(2, partRepo.count());
        YaPart[] parts = new YaPart[ent.getForecast().getParts().size()];
        parts = ent.getForecast().getParts().toArray(parts);
        assertEquals(2, parts.length);
        assertNotNull(parts[0].getPartId());
        assertNotNull(parts[0].getForecast());
        assertNotNull(parts[0].getForecast().getForecastId());
    }

    @Test
    public void testFindLastMessage(@Qualifier("message") YaMessage mes) {
        YaMessage ent = dao.saveMessage(mes, geonameId);
        final YaMessage ent2 = dao.findLastMessage(geonameId);
        assertEquals(ent, ent2);
    }

    @Test
    public void testFindFacts(@Qualifier("message") YaMessage mes) {
        final YaMessage ent = dao.saveMessage(mes, geonameId);
        final List<YaFact> fact2List = dao.findFacts(geonameId,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertEquals(1, fact2List.size());
        assertEquals(ent.getFact(), fact2List.get(0));
        assertNotNull(fact2List.get(0).getFactId());
    }

    @Test
    public void testFindFactsViaSpecification(@Qualifier("message") YaMessage mes) {
        final YaMessage ent = dao.saveMessage(mes, geonameId);
        final List<YaFact> fact2List = dao.findFactsViaSpecification(geonameId,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertEquals(1, fact2List.size());
        assertEquals(ent.getFact(), fact2List.get(0));
        assertNotNull(fact2List.get(0).getFactId());
    }

    @Test
    public void testFindMessages(@Qualifier("message") YaMessage mes) {
        final YaMessage ent = dao.saveMessage(mes, geonameId);
        assertEquals(1, msgRepo.count());
        final List<YaMessage> ent2List = dao.findMessages(geonameId,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertEquals(1, ent2List.size());
        assertEquals(ent, ent2List.get(0));
    }

    @Test
    public void testFindMessagesViaSpecification(@Qualifier("message") YaMessage mes) {
        final YaMessage ent = dao.saveMessage(mes, geonameId);
        assertEquals(1, msgRepo.count());
        final List<YaMessage> ent2List = dao.findMessagesViaSpecification(geonameId,
                LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault()),
                LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault()));
        assertEquals(1, ent2List.size());
        assertEquals(ent, ent2List.get(0));
    }

    @Test
    public void testFindMessageByUuid(@Qualifier("message") YaMessage ent1) {
        UUID uuid = UUID.randomUUID();
        ent1.setMessageUuid(uuid);
        ent1 = dao.saveMessage(ent1, geonameId);
        final YaMessage ent2 = dao.findMessageByUuid(uuid);
        assertEquals(ent1, ent2);
        assertEquals(ent1.getMessageUuid(), ent2.getMessageUuid());
    }

    @AfterEach
    public void tearDown() {
        dao.deleteMessages();
        assertEquals(0, partRepo.count());
        assertEquals(0, foreRepo.count());
        assertEquals(0, factRepo.count());
        assertEquals(0, msgRepo.count());
    }

}
