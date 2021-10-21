/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;

import java.util.List;

public interface YaMessageService {
    void saveMessageToDb(YaMessageDto dto, Integer geonameId);

    List<YaFactDto> getFacts(Integer geonameId, String dateFrom, String dateTo);

    YaMessageDto getLastMessage(Integer geonameId);

    YaMessageDto getMessage(String messageUuid);

    List<YaMessageDto> getMessages(Integer geonameId, String dateFrom, String dateTo);
}
