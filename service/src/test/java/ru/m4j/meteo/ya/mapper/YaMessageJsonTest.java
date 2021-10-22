/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import ru.m4j.meteo.ya.YaTestApplication;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.model.YaMessageDto;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = YaTestApplication.class)
class YaMessageJsonTest {

    private static final String testDataFile = "ya_v1.json";
    @Autowired
    private ObjectMapper jacksonMapper;
    @Value("${meteo.test.data.path}")
    private String testDataPath;
    @Autowired
    private YaMessageDtoModelMapper mapper;

    @BeforeEach
    public void setUp() {
        assertNotNull(mapper);
    }

    private YaMessageDto readJson() throws IOException {
        final FileInputStream fis = new FileInputStream(testDataPath + testDataFile);
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            return jacksonMapper.readValue(rd, YaMessageDto.class);
        }
    }

    @Test
    public void testFullMessageJson(@Qualifier("message") YaMessage entity) throws IOException {
        YaMessageDto dto = readJson();
        dto.setInfo(null);
        entity.setMessageUuid(null);
        entity.setCreatedOn(null);
        final YaMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        assertNotNull(dto2);
        assertEquals(dto, dto2);
    }

    @Test
    public void testJacksonMapper() throws IOException {
        YaMessageDto dto = readJson();
        assertTrue(dto.getNow().toEpochMilli() > 0);
        String json = jacksonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        assertTrue(json.length() > 10);
    }


}
