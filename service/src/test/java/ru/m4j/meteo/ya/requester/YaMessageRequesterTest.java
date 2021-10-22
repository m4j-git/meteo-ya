/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.requester;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;
import ru.m4j.meteo.ya.YaTestApplication;
import ru.m4j.meteo.ya.model.GeonameDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.repo.YaMessageRepository;
import ru.m4j.meteo.ya.service.YaDao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = YaTestApplication.class)
@Transactional
class YaMessageRequesterTest {

    private static final String testDataFile = "ya_v1.json";
    @MockBean
    YaMessageClient client;
    @Autowired
    private YaMessageRequester requester;
    @Autowired
    private ObjectMapper jacksonMapper;
    @Autowired
    private YaDao dao;
    @Value("${meteo.test.data.path}")
    private String testDataPath;
    @Autowired
    private YaMessageRepository msgRepo;

    @BeforeEach
    public void setUp() {
        assertNotNull(requester);
        assertEquals(0, msgRepo.count());
    }

    @Test
    public void testRequestProvider(@Autowired GeonameDto geoname) throws IOException {
        when(client.request(requester.getUri(geoname))).thenReturn(readJson());
        final YaMessageDto result = requester.requestProvider(geoname);
        assertNotNull(result.getNow());
    }

    private YaMessageDto readJson() throws IOException {
        final FileInputStream fis = new FileInputStream(testDataPath + testDataFile);
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            return jacksonMapper.readValue(rd, YaMessageDto.class);
        }
    }

    @AfterEach
    public void tearDown() {
        dao.deleteMessages();
        assertEquals(0, msgRepo.count());
    }

}
