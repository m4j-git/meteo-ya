/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ya.config.YaTestBeanFactory;
import ru.m4j.meteo.ya.config.YaTestBeanSource;
import ru.m4j.meteo.ya.config.YaTestDomainConfiguration;
import ru.m4j.meteo.ya.domain.YaFact;
import ru.m4j.meteo.ya.domain.YaForecast;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaForecastDto;
import ru.m4j.meteo.ya.model.YaMessageDto;

@Slf4j
@SpringBootTest(classes = { YaTestDomainConfiguration.class })
@Import(YaTestBeanFactory.class)
class YaMessageDtoModelMapperTest {

    @Autowired
    private YaTestBeanSource src;
    @Autowired
    private YaMessageDtoModelMapper mapper;

    @BeforeEach
    public void setUp() {
        assertThat(mapper).isNotNull();
    }

    @Test
    void testMessageMapper() throws IOException {
        YaMessageDto dto = src.readJson();
        dto.setInfo(null);
        dto.setFact(null);
        dto.setForecast(null);
        final YaMessage entity = mapper.messageDtoToMessage(dto);
        final YaMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        dto2.setMessageUuid(null);
        dto2.setCreatedOn(null);
        assertThat(dto2).isNotNull()
            .isEqualTo(dto)
            .hasSameHashCodeAs(dto);
        log.info("dto" + dto2);
    }

    @Test
    void testFactMapper() throws IOException {
        YaMessageDto dto = src.readJson();
        final YaFactDto dto1 = dto.getFact();
        final YaFact entity = mapper.factDtoToFact(dto1);
        final YaFactDto dto2 = mapper.factDtoFromFact(entity);
        assertThat(dto2).isNotNull()
            .isEqualTo(dto1)
            .hasSameHashCodeAs(dto1);
    }

    @Test
    void testForecastMapper() throws IOException {
        YaMessageDto dto = src.readJson();
        final YaForecastDto dto1 = dto.getForecast();
        final YaForecast entity = mapper.forecastDtoToForecast(dto1);
        final YaForecastDto dto2 = mapper.forecastDtoFromForecast(entity);
        assertThat(dto2).isNotNull()
            .isEqualTo(dto1)
            .hasSameHashCodeAs(dto1);
    }

    @Test
    void testFullMessageMapper() throws IOException {
        YaMessageDto dto = src.readJson();
        dto.setInfo(null);
        final YaMessage entity = mapper.messageDtoToMessage(dto);
        final YaMessageDto dto2 = mapper.messageDtoFromMessage(entity);
        dto2.setMessageUuid(null);
        dto2.setCreatedOn(null);
        assertThat(dto2).isNotNull()
            .isEqualTo(dto)
            .hasSameHashCodeAs(dto);
        log.info("dto" + dto);
    }

}
