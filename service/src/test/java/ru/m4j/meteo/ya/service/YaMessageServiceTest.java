/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.m4j.meteo.share.app.GlobalConstants;
import ru.m4j.meteo.ya.YaTestApplication;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.repo.YaFactRepository;
import ru.m4j.meteo.ya.repo.YaForecastRepository;
import ru.m4j.meteo.ya.repo.YaMessageRepository;
import ru.m4j.meteo.ya.repo.YaPartRepository;

@SpringBootTest(classes = YaTestApplication.class)
@Transactional
class YaMessageServiceTest {

    private static final String TEST_DATA_FILE = "ya_v1.json";

    private final Integer geonameId = 1;
    private final String messageUuid = "11111111-1111-1111-1111-111111111111";

    @Autowired
    private YaMessageService service;
    @Autowired
    private YaDao dao;
    @Autowired
    private ObjectMapper jacksonMapper;
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
        YaMessageDto dto;
        FileInputStream fis = new FileInputStream(GlobalConstants.TEST_DATA_PATH + TEST_DATA_FILE);
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            dto = jacksonMapper.readValue(rd, YaMessageDto.class);
        }
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
        final List<YaFactDto> fact2List = service.getFacts(geonameId, null, null);
        assertThat(fact2List.size()).isEqualTo(1);
        assertThat(fact2List.get(0)).isNotNull();
    }

    @Test
    void testGetMessages() {
        final List<YaMessageDto> ent2List = service.getMessages(geonameId, null, null);
        assertThat(ent2List.size()).isEqualTo(1);
        assertThat(ent2List.get(0)).isNotNull();
    }

    @Test
    void testGetMessage() {
        final YaMessageDto dto = service.getMessage("11111111-1111-1111-1111-111111111111");
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
