/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.service;

import com.woodapiary.meteo.ya.model.YaFactDto;
import com.woodapiary.meteo.ya.model.YaMessageDto;

import java.util.List;

public interface YaMessageService {
    void saveMessageToDb(YaMessageDto dto, Integer geonameId);

    List<YaFactDto> getFacts(Integer geonameId, String dateFrom, String dateTo);

    YaMessageDto getLastMessage(Integer geonameId);

    YaMessageDto getMessage(String messageUuid);

    List<YaMessageDto> getMessages(Integer geonameId, String dateFrom, String dateTo);
}
