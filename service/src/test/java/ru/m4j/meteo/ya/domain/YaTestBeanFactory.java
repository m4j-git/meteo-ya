/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import ru.m4j.meteo.ya.model.GeonameDto;

@Configuration
public class YaTestBeanFactory {
    private final Integer geonameId = 1;
    private final String messageUuid = "11111111-1111-1111-1111-111111111111";

    @Bean
    @Scope("prototype")
    GeonameDto createGeoname() {
        return new GeonameDto(geonameId, "Moscow", 55.75, 37.6);
    }

    @Bean(name = "message_skinny")
    @Scope("prototype")
    YaMessage createMessage() {
        return createMessage(null, null);
    }

    @Bean(name = "message")
    @Scope("prototype")
    YaMessage createMessage(final YaFact fact, final YaForecast forecast) {
        final YaMessage entity = new YaMessage();
        entity.setMessageUuid(UUID.fromString(messageUuid));
        OffsetDateTime odt1 = OffsetDateTime.ofInstant(Instant.ofEpochSecond(1570198988L), ZoneId.of("UTC"));
        OffsetDateTime odt2 = OffsetDateTime.parse("2019-10-04T14:23:08.537Z");
        entity.setNowDt(odt2);
        entity.setNow(odt1);
        entity.setGeonameId(geonameId);
        if (fact != null) {
            entity.addFact(fact);
        }
        if (forecast != null) {
            entity.addForecast(forecast);
        }
        return entity;
    }

    @Bean
    @Scope("prototype")
    YaFact createFact() {
        final YaFact entity = new YaFact();
        entity.setCondition("overcast");
        entity.setDaytime(YaDaytime.D);
        entity.setFeelsLike((short) 6);
        entity.setHumidity((short) 79);
        entity.setIcon("ovc");
        OffsetDateTime odt1 = OffsetDateTime.ofInstant(Instant.ofEpochSecond(1570197600L), ZoneId.of("UTC"));
        entity.setObsTime(odt1);
        entity.setPolar(false);
        entity.setPressureMm((short) 742);
        entity.setPressurePa((short) 990);
        entity.setSeason(YaSeason.AUTUMN);
        entity.setTemp((short) 9);
        entity.setWindDir("w");
        entity.setWindGust((short) 730);
        entity.setWindSpeed((short) 270);
        return entity;
    }

    @Bean(name = "forecast_skinny")
    @Scope("prototype")
    YaForecast createForecast() {
        return createForecast(null);
    }

    @Bean(name = "forecast")
    @Scope("prototype")
    YaForecast createForecast(List<YaPart> parts) {
        final YaForecast entity = new YaForecast();
        entity.setDate(LocalDate.parse("2019-10-04", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        OffsetDateTime odt1 = OffsetDateTime.ofInstant(Instant.ofEpochSecond(1570136400L), ZoneId.of("UTC"));
        entity.setDateTs(odt1);
        entity.setWeek((byte) 40);
        entity.setMoonCode((byte) 11);
        entity.setMoonText("growing-moon");
        entity.setSunrise(LocalTime.parse("06:37", DateTimeFormatter.ofPattern("HH:mm")));
        entity.setSunset(LocalTime.parse("17:58", DateTimeFormatter.ofPattern("HH:mm")));
        if (parts != null) {
            parts.forEach(entity::addPart);
        }
        return entity;
    }

    @Bean
    @Scope("prototype")
    List<YaPart> createPartList(@Qualifier("part") final YaPart part, @Qualifier("part2") final YaPart part2) {
        return List.of(part, part2);
    }

    @Bean(name = "part")
    @Scope("prototype")
    @Primary
    YaPart createPart() {
        final YaPart entity = new YaPart();
        entity.setCondition("cloudy");
        entity.setDaytime("n");
        entity.setFeelsLike((short) 5);
        entity.setHumidity((short) 82);
        entity.setIcon("bkn_n");
        entity.setPolar(false);
        entity.setPressureMm((short) 743);
        entity.setPressurePa((short) 991);
        entity.setPartName("evening");
        entity.setTempMin((short) 7);
        entity.setTempMax((short) 9);
        entity.setTempAvg((short) 8);
        entity.setWindDir("w");
        entity.setWindGust((short) 550);
        entity.setWindSpeed((short) 240);
        entity.setPrecMm((short) 0.0);
        entity.setPrecPeriod((short) 360);
        entity.setPrecProb((short) 0);
        return entity;
    }

    @Bean(name = "part2")
    @Scope("prototype")
    YaPart createPart2() {
        final YaPart entity = new YaPart();
        entity.setCondition("partly-cloudy");
        entity.setDaytime("n");
        entity.setFeelsLike((short) 4);
        entity.setHumidity((short) 91);
        entity.setIcon("bkn_n");
        entity.setPolar(false);
        entity.setPressureMm((short) 744);
        entity.setPressurePa((short) 992);
        entity.setPartName("night");
        entity.setTempMin((short) 5);
        entity.setTempMax((short) 7);
        entity.setTempAvg((short) 6);
        entity.setWindDir("w");
        entity.setWindGust((short) 250);
        entity.setWindSpeed((short) 80);
        entity.setPrecMm((short) 0);
        entity.setPrecPeriod((short) 360);
        entity.setPrecProb((short) 0);
        return entity;
    }

}
