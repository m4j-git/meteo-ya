/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.ApiOperation;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;

@RequestMapping("/api/v1")
public interface YaRestResource {

    @ApiOperation(notes = "Yandex messages", value = "get list of weather messages")
    @GetMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    List<YaMessageDto> getMessages(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo);

    @ApiOperation(notes = "Yandex messages", value = "get weather message")
    @GetMapping(value = "/messages/one/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    YaMessageDto getMessage(@PathVariable(value = "uuid") String uuid);

    @ApiOperation(notes = "Yandex messages", value = "get last weather message")
    @GetMapping(value = "/messages/last", produces = MediaType.APPLICATION_JSON_VALUE)
    YaMessageDto getLastMessage(@RequestParam Integer geonameId);

    @ApiOperation(notes = "Yandex messages", value = "get fact weather messages")
    @GetMapping(value = "/messages/facts", produces = MediaType.APPLICATION_JSON_VALUE)
    List<YaFactDto> getFacts(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo);
}
