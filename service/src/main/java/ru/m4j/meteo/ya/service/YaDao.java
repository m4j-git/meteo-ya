/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ru.m4j.meteo.ya.domain.YaFact;
import ru.m4j.meteo.ya.domain.YaMessage;

public interface YaDao {

    YaMessage saveMessage(YaMessage message, Integer geonameId);

    void deleteMessages();

    List<YaFact> findFacts(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo);

    Optional<YaMessage> findLastMessage(Integer geonameId);

    Optional<YaMessage> findMessageByUuid(UUID messageUuid);

    List<YaMessage> findMessages(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo);

    long count(Class<?> clazz);

}
