/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.rest;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.web.util.UriComponentsBuilder;

import ru.m4j.meteo.ya.domain.YaFact;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.service.YaDao;
import ru.m4j.meteo.ya.srv.config.YaMysqlContainerBase;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class YaRestResourceIT extends YaMysqlContainerBase {

    private final String messageUuid = "11111111-1111-1111-1111-111111111111";
    private final Integer geonameId = 1;
    private final String host = "localhost";
    private final String scheme = "http";
    private final String path = "/meteo-ya/api/v1";

    @LocalServerPort
    private int randomServerPort;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private YaDao dao;

    @BeforeEach
    void setUp(@Qualifier("message") YaMessage mes) {
        assertThat(dao.count(YaFact.class)).isZero();
        assertThat(dao.count(YaMessage.class)).isZero();
        Integer geonameId = 1;
        dao.saveMessage(mes, geonameId);
        assertThat(dao.count(YaFact.class)).isEqualTo(1);
        assertThat(dao.count(YaMessage.class)).isEqualTo(1);
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
    void tearDown() {
        dao.deleteMessages();
        assertThat(dao.count(YaFact.class)).isZero();
        assertThat(dao.count(YaMessage.class)).isZero();
    }

}
