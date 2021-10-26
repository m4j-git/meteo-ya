/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.rest;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import ru.m4j.meteo.ya.api.YaRestResource;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.service.YaMessageService;

@SuppressWarnings("unused")
@RestController
public class YaRestResourceImpl implements YaRestResource {

    private final YaMessageService messageService;

    public YaRestResourceImpl(YaMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public List<YaMessageDto> getMessages(Integer geonameId, String dateFrom, String dateTo) {
        return messageService.getMessages(geonameId, dateFrom, dateTo);
    }

    @Override
    public YaMessageDto getMessage(String uuid) {
        return messageService.getMessage(uuid);
    }

    @Override
    public YaMessageDto getLastMessage(Integer geonameId) {
        return messageService.getLastMessage(geonameId);
    }

    @Override
    public List<YaFactDto> getFacts(Integer geonameId, String dateFrom, String dateTo) {
        return messageService.getFacts(geonameId, dateFrom, dateTo);
    }

}