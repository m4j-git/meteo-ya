/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.share.misc.ErrorAttribute;
import ru.m4j.meteo.share.misc.ResourceNotFoundException;
import ru.m4j.meteo.ya.domain.YaFact;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.mapper.YaMessageDtoModelMapper;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;

@Service
@Slf4j
public class YaMessageServiceImpl implements YaMessageService {

    private final YaDao dao;
    private final YaMessageDtoModelMapper mapper;

    public YaMessageServiceImpl(YaDao dao, YaMessageDtoModelMapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void saveMessageToDb(final YaMessageDto dto, String geonameId) {
        YaMessage message = dao.saveMessage(mapper.messageDtoToMessage(dto), Integer.parseInt(geonameId));
        log.info("save yandex weather message to db - ok, id= {}", message.getMessageId());
    }

    @Override
    @Transactional
    public List<YaFactDto> getFacts(String geonameId, String dateFrom, String dateTo) {
        LocalDateTime ldtFrom = dateFromMapper(dateFrom);
        LocalDateTime ldtTo = dateToMapper(dateTo);
        List<YaFact> entityList = dao.findFacts(Integer.parseInt(geonameId), ldtFrom, ldtTo);
        return mapper.factsDtoFromFacts(entityList);
    }

    @Override
    @Transactional
    public YaMessageDto getLastMessage(String geonameId) {
        YaMessage ent = dao.findLastMessage(Integer.parseInt(geonameId)).orElseThrow(
            () -> new ResourceNotFoundException(ErrorAttribute.MESSAGE_ERROR_CODE, ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, geonameId));
        return mapper.messageDtoFromMessage(ent);
    }

    @Override
    @Transactional
    public YaMessageDto getMessage(String messageUuid) {
        YaMessage ent = dao.findMessageByUuid(UUID.fromString(messageUuid)).orElseThrow(
            () -> new ResourceNotFoundException(ErrorAttribute.MESSAGE_ERROR_CODE, ErrorAttribute.RESOURCE_NOT_FOUND_ERROR, messageUuid));
        return mapper.messageDtoFromMessage(ent);
    }

    @Override
    @Transactional
    public List<YaMessageDto> getMessages(String geonameId, String dateFrom, String dateTo) {
        LocalDateTime ldtFrom = dateFromMapper(dateFrom);
        LocalDateTime ldtTo = dateToMapper(dateTo);
        List<YaMessage> ent = dao.findMessages(Integer.parseInt(geonameId), ldtFrom, ldtTo);
        return mapper.messagesDtoFromMessages(ent);
    }

    private LocalDateTime dateToMapper(String dateTo) {
        return dateTo != null ? LocalDateTime.parse(dateTo)
            : LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.MAX_VALUE), ZoneId.systemDefault());
    }

    private LocalDateTime dateFromMapper(String dateFrom) {
        return dateFrom != null ? LocalDateTime.parse(dateFrom) : LocalDateTime.ofInstant(Instant.ofEpochSecond(0), ZoneId.systemDefault());
    }

}
