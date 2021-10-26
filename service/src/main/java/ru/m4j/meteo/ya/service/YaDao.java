/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import ru.m4j.meteo.ya.domain.YaFact;
import ru.m4j.meteo.ya.domain.YaMessage;

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
