/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.requester;

import java.net.URI;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ya.model.LocationDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.service.YaMessageService;

/**
 * GET https://api.weather.yandex.ru/v1/informers?
 * lat=<широта>
 * & lon=<долгота>
 * & [lang=<язык ответа>]
 * <p>
 * X-Yandex-API-Key: <значение ключа>
 */

@Service
@Slf4j
public class YaMessageRequester {

    private static final String SCHEME = "https";
    private static final String HOST = "api.weather.yandex.ru";
    private static final String PATH = "/v1/informers/";

    private final YaMessageService service;
    private final YaMessageClient client;

    public YaMessageRequester(YaMessageService service, YaMessageClient client) {
        this.service = service;
        this.client = client;
    }

    //50 on day.
    public YaMessageDto requestProvider(LocationDto geo) {
        YaMessageDto dto = null;
        try {
            dto = client.request(getUri(geo));
            service.saveMessageToDb(dto, geo.getGeonameId());
            log.info("read yandex weather message ok for {}", geo);
            log.debug(dto.toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ya message is {}", (dto == null ? "null" : dto.toString()));
        }
        return dto;
    }

    URI getUri(LocationDto geo) {
        return UriComponentsBuilder.newInstance().scheme(SCHEME).host(HOST).path(PATH).queryParam("lat", geo.getLat()).queryParam("lon", geo.getLon())
                .buildAndExpand().toUri();
    }

}
