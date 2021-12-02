/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.m4j.meteo.ya.config.YaTestBeanSource;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.model.YaMessageDto;

@SpringBootTest
class YaMessageJsonTest {

    @Autowired
    private ObjectMapper jacksonMapper;
    @Autowired
    private YaMessageDtoModelMapper mapper;
    @Autowired
    private YaTestBeanSource src;

    @BeforeEach
    public void setUp() {
        assertThat(mapper).isNotNull();
    }

    @Test
    void testFullMessageJson(@Qualifier("message") YaMessage entity) throws IOException {
        YaMessageDto dto = src.readJson();
        dto.setInfo(null);
        entity.setMessageUuid(null);
        entity.setCreatedOn(null);
        final YaMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        assertThat(dto2).isNotNull();
        assertThat(dto).isEqualTo(dto2);
    }

    @Test
    void testJacksonMapper() throws IOException {
        YaMessageDto dto = src.readJson();
        assertThat(dto.getNow().toEpochMilli()).isPositive();
        String json = jacksonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        assertThat(json).isNotEmpty();
    }

}
