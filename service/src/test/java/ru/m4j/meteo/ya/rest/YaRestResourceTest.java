/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import ru.m4j.meteo.ya.YaTestApplication;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.repo.YaFactRepository;
import ru.m4j.meteo.ya.repo.YaMessageRepository;
import ru.m4j.meteo.ya.service.YaDao;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = YaTestApplication.class)
class YaRestResourceTest {

    private final String messageUuid = "11111111-1111-1111-1111-111111111111";
    private final Integer geonameId = 1;
    private final String host = "localhost";
    private final String scheme = "http";
    private final String path = "/meteo-ya/api/v1";

    @LocalServerPort
    private int randomServerPort;
    @Autowired
    private YaMessageRepository msgRepo;
    @Autowired
    private YaFactRepository factRepo;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private YaDao dao;

    @BeforeEach
    public void setUp(@Qualifier("message") YaMessage mes) {
        assertEquals(0, factRepo.count());
        assertEquals(0, msgRepo.count());
        Integer geonameId = 1;
        dao.saveMessage(mes, geonameId);
        assertEquals(1, factRepo.count());
        assertEquals(1, msgRepo.count());
        assertThat(restTemplate).isNotNull();
        restTemplate.getRestTemplate().setErrorHandler(new YaRestTemplateResponseErrorHandler());
    }

    @Test
    void testGetFacts() {
        URI uri = UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(randomServerPort).path(path).pathSegment("messages/facts")
                .queryParam("geonameId", geonameId).buildAndExpand().toUri();
        ResponseEntity<YaFactDto[]> response = restTemplate.getForEntity(uri, YaFactDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isSameAs(1);
    }

    @Test
    void testGetLastMessage() {
        URI uri = UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(randomServerPort).path(path).pathSegment("messages/last")
                .queryParam("geonameId", geonameId).buildAndExpand().toUri();
        ResponseEntity<YaMessageDto> response = restTemplate.getForEntity(uri, YaMessageDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testGetMessages() {
        URI uri = UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(randomServerPort).path(path).pathSegment("messages")
                .queryParam("geonameId", geonameId).buildAndExpand().toUri();
        ResponseEntity<YaMessageDto[]> response = restTemplate.getForEntity(uri, YaMessageDto[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isSameAs(1);
    }

    @Test
    void testGetMessage() {
        URI uri = UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(randomServerPort).path(path).pathSegment("messages/one/{messageUuid}")
                .buildAndExpand(messageUuid).toUri();
        ResponseEntity<YaMessageDto> response = restTemplate.getForEntity(uri, YaMessageDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @AfterEach
    public void tearDown() {
        dao.deleteMessages();
        assertEquals(0, factRepo.count());
        assertEquals(0, msgRepo.count());
    }

}
