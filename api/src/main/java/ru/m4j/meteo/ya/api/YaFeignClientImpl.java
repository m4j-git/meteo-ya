/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.api;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;

@ConditionalOnProperty(name = "meteo.client.type", havingValue = "feign", matchIfMissing = false)
@FeignClient(name = "meteo-ya")
public interface YaFeignClientImpl extends YaRestResource {

    @Override
    @GetMapping(value = "/meteo-ya/api/v1/messages", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<YaMessageDto> getMessages(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo);

    @Override
    @GetMapping(value = "/meteo-ya/api/v1/messages/one/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    YaMessageDto getMessage(@PathVariable(value = "uuid") String uuid);

    @GetMapping(value = "/meteo-ya/api/v1/messages/last", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    YaMessageDto getLastMessage(@RequestParam Integer geonameId);

    @GetMapping(value = "/meteo-ya/api/v1/messages/facts", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    List<YaFactDto> getFacts(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo);

}
