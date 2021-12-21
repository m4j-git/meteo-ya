/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import ru.m4j.meteo.ya.domain.YaFact;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.repo.YaFactRepository;
import ru.m4j.meteo.ya.repo.YaForecastRepository;
import ru.m4j.meteo.ya.repo.YaMessageRepository;
import ru.m4j.meteo.ya.repo.YaPartRepository;

@Repository
public class YaDaoImplV2JpaSpec extends YaDaoImplV1Jpa {

    public YaDaoImplV2JpaSpec(YaMessageRepository messageRepo, YaFactRepository factRepo, YaForecastRepository foreRepo, YaPartRepository partRepo,
        EntityManager em) {
        super(messageRepo, factRepo, foreRepo, partRepo, em);
    }

    @Override
    @Transactional
    public List<YaFact> findFacts(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return factRepo.findAll(factSpecification(geonameId, dateFrom, dateTo));
    }

    @Override
    @Transactional
    public List<YaMessage> findMessages(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return messageRepo.findAll(messageSpecification(geonameId, dateFrom, dateTo));
    }

    Specification<YaFact> factSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return (root, query, builder) -> {
            Join<YaFact, YaMessage> join = root.join("message");
            return builder.and(builder.equal(join.get("geonameId"), geonameId), builder.between(join.get("createdOn"), dateFrom, dateTo));
        };
    }

    Specification<YaMessage> messageSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return (root, query, builder) -> builder.and(builder.equal(root.get("geonameId"), geonameId),
            builder.between(root.get("createdOn"), dateFrom, dateTo));
    }

}
