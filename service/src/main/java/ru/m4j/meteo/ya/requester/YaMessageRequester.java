/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.requester;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import ru.m4j.meteo.ya.model.GeonameDto;
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
public class YaMessageRequester {

    private static final Logger log = LoggerFactory.getLogger(YaMessageRequester.class);

    private final static String scheme = "https";
    private final static String host = "api.weather.yandex.ru";
    private final static String path = "/v1/informers/";

    private final YaMessageService service;
    private final YaMessageClient client;

    public YaMessageRequester(YaMessageService service, YaMessageClient client) {
        this.service = service;
        this.client = client;
    }

    //50 on day.
    public YaMessageDto requestProvider(GeonameDto geo) {
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

    URI getUri(GeonameDto geo) {
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .path(path)
                .queryParam("lat", geo.getLat())
                .queryParam("lon", geo.getLon())
                .buildAndExpand()
                .toUri();
    }


}
