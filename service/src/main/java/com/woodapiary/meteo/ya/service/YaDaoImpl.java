/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.service;

import com.woodapiary.meteo.ya.domain.YaFact;
import com.woodapiary.meteo.ya.domain.YaMessage;
import com.woodapiary.meteo.ya.repo.YaFactRepository;
import com.woodapiary.meteo.ya.repo.YaForecastRepository;
import com.woodapiary.meteo.ya.repo.YaMessageRepository;
import com.woodapiary.meteo.ya.repo.YaPartRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Join;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class YaDaoImpl implements YaDao {

    private final YaMessageRepository messageRepo;
    private final YaFactRepository factRepo;
    private final YaForecastRepository foreRepo;
    private final YaPartRepository partRepo;

    public YaDaoImpl(YaMessageRepository messageRepo, YaFactRepository factRepo, YaForecastRepository foreRepo, YaPartRepository partRepo) {
        this.messageRepo = messageRepo;
        this.factRepo = factRepo;
        this.foreRepo = foreRepo;
        this.partRepo = partRepo;
    }

    @Override
    @Transactional
    public YaMessage saveMessage(final YaMessage entity, Integer geonameId) {
        entity.setGeonameId(geonameId);
        if (entity.getMessageId() == null) {
            if (entity.getMessageUuid() == null) {
                entity.setMessageUuid(UUID.randomUUID());
            }
            Long id = messageRepo.findIdByMessageUuid(entity.getMessageUuid());
            if (id != null) {
                throw new IllegalStateException("entity exists");
            }
        }
        return messageRepo.save(entity);
    }

    @Override
    @Transactional
    public void deleteMessages() {
        partRepo.deleteAll();
        foreRepo.deleteAll();
        factRepo.deleteAll();
        messageRepo.deleteAll();
    }

    @Override
    @Transactional
    public List<YaFact> findFacts(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return factRepo.findFacts(geonameId, dateFrom, dateTo);
    }

    @Override
    @Transactional
    public List<YaFact> findFactsViaSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return factRepo.findAll(factSpecification(geonameId, dateFrom, dateTo));
    }

    @Override
    @Transactional
    public YaMessage findLastMessage(Integer geonameId) {
        return messageRepo.findTopByGeonameIdOrderByCreatedOnDesc(geonameId);
    }

    @Override
    public YaMessage findMessageByUuid(UUID messageUuid) {
        return messageRepo.findMessageByUuid(messageUuid).orElseThrow();
    }

    @Override
    @Transactional
    public List<YaMessage> findMessages(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return messageRepo.findMessages(geonameId, dateFrom, dateTo);
    }

    @Override
    @Transactional
    public List<YaMessage> findMessagesViaSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return messageRepo.findAll(messageSpecification(geonameId, dateFrom, dateTo));
    }

    public Specification<YaFact> factSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return (root, query, builder) -> {
            Join<YaFact, YaMessage> join = root.join("message");
            return builder.and(
                    builder.equal(join.get("geonameId"), geonameId),
                    builder.between(join.get("createdOn"), dateFrom, dateTo));
        };
    }

    public Specification<YaMessage> messageSpecification(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return (root, query, builder) -> builder.and(
                builder.equal(root.get("geonameId"), geonameId),
                builder.between(root.get("createdOn"), dateFrom, dateTo));
    }

}



