/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.client;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;

@ConditionalOnProperty(name = "meteo.client.type", havingValue = "resttemplate", matchIfMissing = false)
@Service
@Slf4j
public class YaRestClientImpl implements YaRestResource {

    private final RestTemplate restTemplate;

    @Value("${meteo.ya-rest-client.host:meteo-host}")
    private String host;
    @Value("${meteo.ya-rest-client.scheme:http}")
    private String scheme;
    @Value("${meteo.ya-rest-client.path:/meteo-ya/api/v1}")
    private String path;
    @Value("${meteo.ya-rest-client.port:8083}")
    private Integer port;

    public YaRestClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    URI getUri(String segment, Object... param) {
        return UriComponentsBuilder.newInstance().scheme(scheme).host(host).port(port).path(path).pathSegment(segment)
            .queryParam("geonameId", param[0]).buildAndExpand().toUri();
    }

    @Override
    public List<YaFactDto> getFacts(String geonameId, String dateFrom, String dateTo) {
        URI uri = getUri("messages/facts", geonameId);
        ResponseEntity<YaFactDto[]> response = restTemplate.getForEntity(uri, YaFactDto[].class);
        if ((response.getStatusCode() == HttpStatus.OK) && response.hasBody()) {
            return Arrays.asList(response.getBody());
        }
        return Collections.emptyList();
    }

    @Override
    public YaMessageDto getLastMessage(String geonameId) {
        URI uri = getUri("messages/last", geonameId);
        ResponseEntity<YaMessageDto> response = restTemplate.getForEntity(uri, YaMessageDto.class);
        if ((response.getStatusCode() == HttpStatus.OK) && response.hasBody()) {
            log.debug("response " + response.getBody());
            return response.getBody();
        }
        return null;
    }

    @Override
    public List<YaMessageDto> getMessages(String geonameId, String dateFrom, String dateTo) {
        URI uri = getUri("messages", geonameId);
        ResponseEntity<YaMessageDto[]> response = restTemplate.getForEntity(uri, YaMessageDto[].class);
        if ((response.getStatusCode() == HttpStatus.OK) && response.hasBody()) {
            return Arrays.asList(response.getBody());
        }
        return Collections.emptyList();
    }

    @Override
    public YaMessageDto getMessage(String uuid) {
        throw new IllegalStateException("not implemented");
    }

}
