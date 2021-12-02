/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.requester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import ru.m4j.meteo.ya.config.YaTestBeanSource;
import ru.m4j.meteo.ya.model.LocationDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.repo.YaMessageRepository;
import ru.m4j.meteo.ya.service.YaDao;

@SpringBootTest
@Transactional
class YaMessageRequesterTest {

    @MockBean
    YaMessageClient client;
    @Autowired
    private YaMessageRequester requester;
    @Autowired
    private YaTestBeanSource src;
    @Autowired
    private YaDao dao;
    @Autowired
    private YaMessageRepository msgRepo;

    @BeforeEach
    public void setUp() {
        assertThat(requester).isNotNull();
        assertThat(msgRepo.count()).isZero();
    }

    @Test
    void testRequestProvider(@Autowired LocationDto location) throws IOException {
        when(client.request(requester.getUri(location))).thenReturn(src.readJson());
        final YaMessageDto result = requester.requestProvider(location);
        assertThat(result.getNow()).isNotNull();
    }

    @AfterEach
    public void tearDown() {
        dao.deleteMessages();
        assertThat(msgRepo.count()).isZero();
    }

}
