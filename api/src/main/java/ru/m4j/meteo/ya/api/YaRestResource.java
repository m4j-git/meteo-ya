/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.api;

import java.util.List;

import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;

public interface YaRestResource {

    List<YaMessageDto> getMessages(Integer geonameId, String dateFrom, String dateTo);

    YaMessageDto getMessage(String uuid);

    YaMessageDto getLastMessage(Integer geonameId);

    List<YaFactDto> getFacts(Integer geonameId, String dateFrom, String dateTo);
}
