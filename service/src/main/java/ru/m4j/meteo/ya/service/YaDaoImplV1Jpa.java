/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import ru.m4j.meteo.ya.domain.YaFact;
import ru.m4j.meteo.ya.domain.YaForecast;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.domain.YaPart;
import ru.m4j.meteo.ya.repo.YaFactRepository;
import ru.m4j.meteo.ya.repo.YaForecastRepository;
import ru.m4j.meteo.ya.repo.YaMessageRepository;
import ru.m4j.meteo.ya.repo.YaPartRepository;

@Repository
@Primary
public class YaDaoImplV1Jpa implements YaDao {

    protected final YaMessageRepository messageRepo;
    protected final YaFactRepository factRepo;
    protected final YaForecastRepository foreRepo;
    protected final YaPartRepository partRepo;
    protected final EntityManager em;

    private static final String QUERY_LAST_MESSAGE = "select msg from YaMessage as msg where msg.geonameId=:geoname_id "
        + "ORDER BY msg.createdOn desc";

    public YaDaoImplV1Jpa(YaMessageRepository messageRepo, YaFactRepository factRepo, YaForecastRepository foreRepo, YaPartRepository partRepo,
        EntityManager em) {
        this.messageRepo = messageRepo;
        this.factRepo = factRepo;
        this.foreRepo = foreRepo;
        this.partRepo = partRepo;
        this.em = em;
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
    public Optional<YaMessage> findLastMessage(Integer geonameId) {
        return Optional.ofNullable((YaMessage) em.createQuery(QUERY_LAST_MESSAGE)
            .setMaxResults(1)
            .setParameter("geoname_id", geonameId)
            .getSingleResult());
    }

    @Override
    public Optional<YaMessage> findMessageByUuid(UUID messageUuid) {
        return messageRepo.findMessageByUuid(messageUuid);
    }

    @Override
    @Transactional
    public List<YaMessage> findMessages(Integer geonameId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return messageRepo.findMessages(geonameId, dateFrom, dateTo);
    }

    @Override
    public long count(Class<?> clazz) {
        if (clazz == YaMessage.class) {
            return messageRepo.count();
        }
        if (clazz == YaFact.class) {
            return factRepo.count();
        }
        if (clazz == YaForecast.class) {
            return foreRepo.count();
        }
        if (clazz == YaPart.class) {
            return partRepo.count();
        }
        throw new IllegalStateException();
    }

}
