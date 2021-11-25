/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.m4j.meteo.ya.domain.YaFact;

public interface YaFactRepository extends JpaRepository<YaFact, Long>, JpaSpecificationExecutor<YaFact> {

    @Query("select fact from YaFact as fact where fact.message.messageId in "
        + "(select messageId from YaMessage where geonameId=:geoname_id and createdOn BETWEEN :date_from AND :date_to)")
    List<YaFact> findFacts(@Param("geoname_id") Integer geonameId, @Param("date_from") LocalDateTime dateFrom,
        @Param("date_to") LocalDateTime dateTo);
}
