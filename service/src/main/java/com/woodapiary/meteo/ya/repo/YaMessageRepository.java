/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.repo;

import com.woodapiary.meteo.ya.domain.YaMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface YaMessageRepository extends JpaRepository<YaMessage, Long>, JpaSpecificationExecutor<YaMessage> {

    YaMessage findTopByGeonameIdOrderByCreatedOnDesc(Integer geonameId);

    @Query(value = "select msg from YaMessage as msg  where msg.geonameId=:geoname_id " +
            "and msg.createdOn BETWEEN :date_from AND :date_to ORDER BY msg.createdOn desc")
    List<YaMessage> findMessages(@Param("geoname_id") Integer geonameId, @Param("date_from") LocalDateTime dateFrom, @Param("date_to") LocalDateTime dateTo);

    @Query(value = "select msg.messageId from YaMessage as msg  where msg.messageUuid= :message_uuid")
    Long findIdByMessageUuid(@Param("message_uuid") UUID uuid);

    @Query(value = "select msg from YaMessage as msg  where msg.messageUuid= :message_uuid")
    Optional<YaMessage> findMessageByUuid(@Param("message_uuid") UUID uuid);
}
