/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.rest;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import ru.m4j.meteo.ya.api.YaRestResource;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.service.YaMessageService;

@RestController
@RequestMapping("/api/v1")
public class YaRestResourceImpl implements YaRestResource {

    private final YaMessageService messageService;

    public YaRestResourceImpl(YaMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    @Operation(tags = "yandex messages", summary = "get list of weather messages")
    @GetMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<YaMessageDto> getMessages(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
        @RequestParam(required = false) String dateTo) {
        return messageService.getMessages(geonameId, dateFrom, dateTo);
    }

    @Operation(tags = "yandex messages", summary = "get weather message")
    @GetMapping(value = "/messages/one/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public YaMessageDto getMessage(@PathVariable("uuid") String uuid) {
        return messageService.getMessage(uuid);
    }

    @Operation(tags = "yandex messages", summary = "get last weather message")
    @GetMapping(value = "/messages/last", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public YaMessageDto getLastMessage(@RequestParam Integer geonameId) {
        return messageService.getLastMessage(geonameId);
    }

    @Operation(tags = "yandex messages", summary = "get fact weather messages")
    @GetMapping(value = "/messages/facts", produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public List<YaFactDto> getFacts(@RequestParam Integer geonameId, @RequestParam(required = false) String dateFrom,
        @RequestParam(required = false) String dateTo) {
        return messageService.getFacts(geonameId, dateFrom, dateTo);
    }

}
