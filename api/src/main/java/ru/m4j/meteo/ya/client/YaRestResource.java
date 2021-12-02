/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.client;

import java.util.List;

import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;

public interface YaRestResource {

    List<YaMessageDto> getMessages(String geonameId, String dateFrom, String dateTo);

    YaMessageDto getMessage(String uuid);

    YaMessageDto getLastMessage(String geonameId);

    List<YaFactDto> getFacts(String geonameId, String dateFrom, String dateTo);
}
