/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.requester;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ya.model.LocationDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.service.YaMessageService;

/**
 * GET https://api.weather.yandex.ru/v1/informers?
 * lat=широта
 * & lon=долгота
 * & [lang=язык ответа]
 * X-Yandex-API-Key: значение ключа
 */

@Slf4j
@Service
public class YaMessageRequester {

    @Value("${meteo.provider.host:api.weather.yandex.ru}")
    private String host;
    @Value("${meteo.provider.scheme:https}")
    private String scheme;
    @Value("${meteo.provider.path:/v1/informers/}")
    private String path;

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
            log.debug("response " + dto);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("ya message is {}", (dto == null ? "null" : String.valueOf(dto)));
        }
        return dto;
    }

    URI getUri(LocationDto geo) {
        return UriComponentsBuilder.newInstance()
            .scheme(scheme)
            .host(host)
            .path(path)
            .queryParam("lat", geo.getLat())
            .queryParam("lon", geo.getLon())
            .buildAndExpand().toUri();
    }

}
