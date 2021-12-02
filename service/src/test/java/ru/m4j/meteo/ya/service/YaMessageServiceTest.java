/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ya.config.YaTestBeanSource;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.repo.YaFactRepository;
import ru.m4j.meteo.ya.repo.YaForecastRepository;
import ru.m4j.meteo.ya.repo.YaMessageRepository;
import ru.m4j.meteo.ya.repo.YaPartRepository;

@SpringBootTest
@Transactional
class YaMessageServiceTest {

    private final String geonameId = "1";
    private final String messageUuid = "11111111-1111-1111-1111-111111111111";

    @Autowired
    private YaMessageService service;
    @Autowired
    private YaDao dao;
    @Autowired
    private YaTestBeanSource src;
    @Autowired
    private YaFactRepository factRepo;
    @Autowired
    private YaMessageRepository msgRepo;
    @Autowired
    private YaForecastRepository foreRepo;
    @Autowired
    private YaPartRepository partRepo;

    @BeforeEach
    public void setUp() throws IOException {
        assertThat(service).isNotNull();
        assertThat(partRepo.count()).isZero();
        assertThat(foreRepo.count()).isZero();
        assertThat(factRepo.count()).isZero();
        assertThat(msgRepo.count()).isZero();
        YaMessageDto dto = src.readJson();
        assertThat(dto.getNow()).isNotNull();
        dto.setMessageUuid(UUID.fromString(messageUuid));
        service.saveMessageToDb(dto, geonameId);
        assertThat(partRepo.count()).isEqualTo(2);
        assertThat(foreRepo.count()).isEqualTo(1);
        assertThat(factRepo.count()).isEqualTo(1);
        assertThat(msgRepo.count()).isEqualTo(1);
    }

    @Test
    void testGetLastMessage() {
        YaMessageDto dto = service.getLastMessage(geonameId);
        assertThat(dto).isNotNull();
        assertThat(dto.getCreatedOn()).isNotNull();
        assertThat(dto.getMessageUuid()).isNotNull();
    }

    @Test
    void testGetFacts() {
        List<YaFactDto> fact2List = service.getFacts(geonameId, null, null);
        assertThat(fact2List.size()).isEqualTo(1);
        assertThat(fact2List.get(0)).isNotNull();
    }

    @Test
    void testGetMessages() {
        List<YaMessageDto> ent2List = service.getMessages(geonameId, null, null);
        assertThat(ent2List.size()).isEqualTo(1);
        assertThat(ent2List.get(0)).isNotNull();
    }

    @Test
    void testGetMessage() {
        YaMessageDto dto = service.getMessage("11111111-1111-1111-1111-111111111111");
        assertThat(dto).isNotNull();
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
