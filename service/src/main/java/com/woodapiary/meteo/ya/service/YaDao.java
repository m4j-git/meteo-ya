/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.service;

import com.woodapiary.meteo.ya.domain.YaFact;
import com.woodapiary.meteo.ya.domain.YaMessage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface YaDao {

    YaMessage saveMessage(YaMessage message, Integer geonameId);

    void deleteMessages();

    List<YaFact> findFacts(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<YaFact> findFactsViaSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo);

    YaMessage findLastMessage(Integer geonameId);

    YaMessage findMessageByUuid(UUID messageUuid);

    List<YaMessage> findMessages(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo);

    List<YaMessage> findMessagesViaSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo);
}
