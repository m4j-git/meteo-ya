/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.rest;

import com.woodapiary.meteo.ya.api.YaRestResource;
import com.woodapiary.meteo.ya.model.YaFactDto;
import com.woodapiary.meteo.ya.model.YaMessageDto;
import com.woodapiary.meteo.ya.service.YaMessageService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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