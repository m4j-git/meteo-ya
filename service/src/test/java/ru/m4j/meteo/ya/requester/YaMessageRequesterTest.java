/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.requester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.m4j.meteo.share.app.GlobalConstants;
import ru.m4j.meteo.ya.YaTestApplication;
import ru.m4j.meteo.ya.model.LocationDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.repo.YaMessageRepository;
import ru.m4j.meteo.ya.service.YaDao;

@SpringBootTest(classes = YaTestApplication.class)
@Transactional
class YaMessageRequesterTest {

    private static final String TEST_DATA_FILE = "ya_v1.json";
    @MockBean
    YaMessageClient client;
    @Autowired
    private YaMessageRequester requester;
    @Autowired
    private ObjectMapper jacksonMapper;
    @Autowired
    private YaDao dao;
    @Autowired
    private YaMessageRepository msgRepo;

    @BeforeEach
    public void setUp() {
        assertNotNull(requester);
        assertThat(0).isEqualTo(msgRepo.count());
    }

    @Test
    void testRequestProvider(@Autowired LocationDto location) throws IOException {
        when(client.request(requester.getUri(location))).thenReturn(readJson());
        final YaMessageDto result = requester.requestProvider(location);
        assertNotNull(result.getNow());
    }

    private YaMessageDto readJson() throws IOException {
        final FileInputStream fis = new FileInputStream(GlobalConstants.TEST_DATA_PATH + TEST_DATA_FILE);
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            return jacksonMapper.readValue(rd, YaMessageDto.class);
        }
    }

    @AfterEach
    public void tearDown() {
        dao.deleteMessages();
        assertThat(0).isEqualTo(msgRepo.count());
    }

}
