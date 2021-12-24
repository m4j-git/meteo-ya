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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import ru.m4j.meteo.ya.domain.YaFact;
import ru.m4j.meteo.ya.domain.YaForecast;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.domain.YaPart;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.srv.config.YaMysqlContainerBase;
import ru.m4j.meteo.ya.srv.config.YaTestBeanSource;
import ru.m4j.meteo.ya.srv.config.YaTestDaoConfiguration;

@SpringBootTest(classes = YaTestDaoConfiguration.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class YaMessageServiceTest extends YaMysqlContainerBase {

    private final Integer geonameId = 1;
    private final String messageUuid = "11111111-1111-1111-1111-111111111111";

    @Autowired
    private YaMessageService service;
    @Autowired
    private YaDao dao;
    @Autowired
    private YaTestBeanSource src;

    @BeforeEach
    void setUp() throws IOException {
        assertThat(service).isNotNull();
        assertThat(dao.count(YaPart.class)).isZero();
        assertThat(dao.count(YaForecast.class)).isZero();
        assertThat(dao.count(YaFact.class)).isZero();
        assertThat(dao.count(YaMessage.class)).isZero();
        YaMessageDto dto = src.readJson();
        assertThat(dto.getNow()).isNotNull();
        dto.setMessageUuid(UUID.fromString(messageUuid));
        service.saveMessageToDb(dto, geonameId);
        assertThat(dao.count(YaPart.class)).isEqualTo(2);
        assertThat(dao.count(YaForecast.class)).isEqualTo(1);
        assertThat(dao.count(YaFact.class)).isEqualTo(1);
        assertThat(dao.count(YaMessage.class)).isEqualTo(1);
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
    void tearDown() {
        dao.deleteMessages();
        assertThat(dao.count(YaPart.class)).isZero();
        assertThat(dao.count(YaForecast.class)).isZero();
        assertThat(dao.count(YaFact.class)).isZero();
        assertThat(dao.count(YaMessage.class)).isZero();
    }

}
