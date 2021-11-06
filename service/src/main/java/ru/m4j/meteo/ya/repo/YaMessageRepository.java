/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.m4j.meteo.ya.domain.YaMessage;

public interface YaMessageRepository extends JpaRepository<YaMessage, Long>, JpaSpecificationExecutor<YaMessage> {

    @Query(value = "select msg from YaMessage as msg  where msg.geonameId=:geoname_id " + "and msg.createdOn BETWEEN :date_from AND :date_to ORDER BY msg.createdOn desc")
    List<YaMessage> findMessages(@Param("geoname_id") Integer geonameId, @Param("date_from") LocalDateTime dateFrom, @Param("date_to") LocalDateTime dateTo);

    @Query(value = "select msg.messageId from YaMessage as msg  where msg.messageUuid= :message_uuid")
    Long findIdByMessageUuid(@Param("message_uuid") UUID uuid);

    @Query(value = "select msg from YaMessage as msg  where msg.messageUuid= :message_uuid")
    Optional<YaMessage> findMessageByUuid(@Param("message_uuid") UUID uuid);
}
