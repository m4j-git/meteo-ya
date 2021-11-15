/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import ru.m4j.meteo.ya.YaTestApplication;
import ru.m4j.meteo.ya.app.YaTestConstants;
import ru.m4j.meteo.ya.domain.YaFact;
import ru.m4j.meteo.ya.domain.YaForecast;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaForecastDto;
import ru.m4j.meteo.ya.model.YaMessageDto;

@SpringBootTest(classes = YaTestApplication.class)
class YaMessageDtoModelMapperTest {

    private static final String testDataFile = "ya_v1.json";
    @Autowired
    private ObjectMapper jacksonMapper;
    @Autowired
    private YaMessageDtoModelMapper mapper;

    @BeforeEach
    public void setUp() {
        assertNotNull(mapper);
    }

    private YaMessageDto readJson() throws IOException {
        final FileInputStream fis = new FileInputStream(YaTestConstants.testDataPath + testDataFile);
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
        assertNotNull(dto2);
        assertEquals(dto, dto2);
        assertEquals(dto.hashCode(), dto2.hashCode());
        assertTrue(dto.toString().length() > 0);
    }

    @Test
    void testFactMapper() throws IOException {
        YaMessageDto dto = readJson();
        final YaFactDto dto1 = dto.getFact();
        final YaFact entity = mapper.factDtoToFact(dto1);
        System.out.println(entity);
        final YaFactDto dto2 = mapper.factDtoFromFact(entity);
        assertNotNull(dto2);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testForecastMapper() throws IOException {
        YaMessageDto dto = readJson();
        final YaForecastDto dto1 = dto.getForecast();
        final YaForecast entity = mapper.forecastDtoToForecast(dto1);
        final YaForecastDto dto2 = mapper.forecastDtoFromForecast(entity);
        assertNotNull(dto2);
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void testFullMessageMapper() throws IOException {
        YaMessageDto dto = readJson();
        dto.setInfo(null);
        final YaMessage entity = mapper.messageDtoToMessage(dto);
        final YaMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        dto2.setMessageUuid(null);
        dto2.setCreatedOn(null);
        assertNotNull(dto2);
        assertEquals(dto, dto2);
        assertEquals(dto.hashCode(), dto2.hashCode());
        assertTrue(dto.toString().length() > 0);
    }

}
