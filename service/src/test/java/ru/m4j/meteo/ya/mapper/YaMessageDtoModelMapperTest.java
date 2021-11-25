/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.m4j.meteo.share.app.GlobalConstants;
import ru.m4j.meteo.ya.YaTestApplication;
import ru.m4j.meteo.ya.domain.YaFact;
import ru.m4j.meteo.ya.domain.YaForecast;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaForecastDto;
import ru.m4j.meteo.ya.model.YaMessageDto;

@SpringBootTest(classes = YaTestApplication.class)
class YaMessageDtoModelMapperTest {

    private static final String TEST_DATA_FILE = "ya_v1.json";
    @Autowired
    private ObjectMapper jacksonMapper;
    @Autowired
    private YaMessageDtoModelMapper mapper;

    @BeforeEach
    public void setUp() {
        assertThat(mapper).isNotNull();
    }

    private YaMessageDto readJson() throws IOException {
        final FileInputStream fis = new FileInputStream(GlobalConstants.TEST_DATA_PATH + TEST_DATA_FILE);
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            return jacksonMapper.readValue(rd, YaMessageDto.class);
        }
    }

    @Test
    void testMessageMapper() throws IOException {
        YaMessageDto dto = readJson();
        dto.setInfo(null);
        dto.setFact(null);
        dto.setForecast(null);
        final YaMessage entity = mapper.messageDtoToMessage(dto);
        final YaMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        dto2.setMessageUuid(null);
        dto2.setCreatedOn(null);
        assertThat(dto2).isNotNull();
        assertAll(
            () -> assertThat(dto).isEqualTo(dto2),
            () -> assertThat(dto).hasSameHashCodeAs(dto2));
        assertThat(dto.toString()).isNotEmpty();
    }

    @Test
    void testFactMapper() throws IOException {
        YaMessageDto dto = readJson();
        final YaFactDto dto1 = dto.getFact();
        final YaFact entity = mapper.factDtoToFact(dto1);
        System.out.println(entity);
        final YaFactDto dto2 = mapper.factDtoFromFact(entity);
        assertThat(dto2).isNotNull();
        assertAll(
            () -> assertThat(dto1).isEqualTo(dto2),
            () -> assertThat(dto1).hasSameHashCodeAs(dto2));
    }

    @Test
    void testForecastMapper() throws IOException {
        YaMessageDto dto = readJson();
        final YaForecastDto dto1 = dto.getForecast();
        final YaForecast entity = mapper.forecastDtoToForecast(dto1);
        final YaForecastDto dto2 = mapper.forecastDtoFromForecast(entity);
        assertThat(dto2).isNotNull();
        assertAll(
            () -> assertThat(dto1).isEqualTo(dto2),
            () -> assertThat(dto1).hasSameHashCodeAs(dto2));
    }

    @Test
    void testFullMessageMapper() throws IOException {
        YaMessageDto dto = readJson();
        dto.setInfo(null);
        final YaMessage entity = mapper.messageDtoToMessage(dto);
        final YaMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        dto2.setMessageUuid(null);
        dto2.setCreatedOn(null);
        assertThat(dto2).isNotNull();
        assertAll(
            () -> assertThat(dto).isEqualTo(dto2),
            () -> assertThat(dto).hasSameHashCodeAs(dto2));
        assertThat(dto.toString()).isNotEmpty();
    }

}
