/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woodapiary.meteo.ya.YaTestApplication;
import com.woodapiary.meteo.ya.model.YaFactDto;
import com.woodapiary.meteo.ya.model.YaMessageDto;
import com.woodapiary.meteo.ya.repo.YaFactRepository;
import com.woodapiary.meteo.ya.repo.YaForecastRepository;
import com.woodapiary.meteo.ya.repo.YaMessageRepository;
import com.woodapiary.meteo.ya.repo.YaPartRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = YaTestApplication.class)
@Transactional
class YaMessageServiceTest {

    private static final String testDataFile = "ya_v1.json";

    private final Integer geonameId = 1;
    private final String messageUuid = "11111111-1111-1111-1111-111111111111";

    @Value("${meteo.test.data.path}")
    private String testDataPath;
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
        assertNotNull(service);
        assertEquals(0, partRepo.count());
        assertEquals(0, foreRepo.count());
        assertEquals(0, factRepo.count());
        assertEquals(0, msgRepo.count());
        YaMessageDto dto;
        final FileInputStream fis = new FileInputStream(testDataPath + testDataFile);
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            dto = jacksonMapper.readValue(rd, YaMessageDto.class);
        }
        assertNotNull(dto.getNow());
        dto.setMessageUuid(UUID.fromString(messageUuid));
        service.saveMessageToDb(dto, geonameId);
        assertEquals(2, partRepo.count());
        assertEquals(1, foreRepo.count());
        assertEquals(1, factRepo.count());
        assertEquals(1, msgRepo.count());
    }

    @Test
    public void testGetLastMessage() {
        final YaMessageDto dto = service.getLastMessage(geonameId);
        assertNotNull(dto);
        assertNotNull(dto.getCreatedOn());
        assertNotNull(dto.getMessageUuid());
    }

    @Test
    public void testGetFacts() {
        final List<YaFactDto> fact2List = service.getFacts(geonameId, null, null);
        assertEquals(1, fact2List.size());
        assertNotNull(fact2List.get(0));
    }

    @Test
    public void testGetMessages() {
        final List<YaMessageDto> ent2List = service.getMessages(geonameId, null, null);
        assertEquals(1, ent2List.size());
        assertNotNull(ent2List.get(0));
    }

    @Test
    public void testGetMessage() {
        final YaMessageDto dto = service.getMessage("11111111-1111-1111-1111-111111111111");
        assertNotNull(dto);
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
