/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.form;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaForecastDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.model.YaPartDto;

@Component
public class YaWeatherFormMapper {

    public List<YaWeatherForm> mapFacts(List<YaFactDto> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list.stream().filter(Objects::nonNull).map(this::mapFact).toList();
    }

    public List<YaWeatherForm> mapParts(YaForecastDto fore) {
        if (fore == null) {
            return Collections.emptyList();
        }
        return fore.getParts().stream().filter(Objects::nonNull).map(p -> mapPart(p, fore)).toList();
    }

    public List<YaMessageForm> mapMessages(List<YaMessageDto> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list.stream().filter(Objects::nonNull).map(this::mapMessage).toList();
    }

    public YaWeatherForm mapFact(YaFactDto dto) {
        YaWeatherForm form = new YaWeatherForm();
        form.setTemp(mapTemperature(dto.getTemp()));
        form.setClouds(null);
        form.setDewPoint(null);
        form.setObsTime(mapDt(dto.getObsTime()));
        form.setFeelsLike(mapTemperature(dto.getFeelsLike()));
        form.setHumidity(dto.getHumidity());
        form.setPressureMm(dto.getPressureMm());
        form.setPrecipM1h(null);
        form.setUvi(null);
        form.setVisibility(null);
        form.setWindDir(dto.getWindDir());
        form.setWindSpeed(dto.getWindSpeed());
        form.setWindGust(dto.getWindGust());
        form.setCondition(dto.getCondition());
        form.setIcon(dto.getIcon());
        form.setTempWater(mapTemperature(dto.getTempWater()));
        return form;
    }

    private String mapDt(Instant instant) {
        return LocalTime.from(instant.atZone(ZoneId.systemDefault())).toString();
    }

    private String mapTemperature(Integer temperature) {
        if (temperature == null) {
            return null;
        }
        if (temperature > 0) {
            return "+" + temperature.toString();
        }
        if (temperature < 0) {
            return "-" + temperature.toString();
        }
        return "0";
    }

    public YaWeatherForm mapPart(YaPartDto dto, YaForecastDto fore) {
        YaWeatherForm form = new YaWeatherForm();
        form.setTemp(mapTemperature(dto.getTempAvg()));
        form.setClouds(null);
        form.setDewPoint(null);
        form.setObsTime(mapDt(fore.getDateTs()));
        form.setFeelsLike(mapTemperature(dto.getFeelsLike()));
        form.setHumidity(dto.getHumidity());
        form.setPressureMm(dto.getPressureMm());
        form.setPrecipM1h(null);
        form.setUvi(null);
        form.setVisibility(null);
        form.setWindDir(dto.getWindDir());
        form.setWindSpeed(dto.getWindSpeed());
        form.setWindGust(dto.getWindGust());
        form.setCondition(dto.getCondition());
        form.setIcon(dto.getIcon());
        return form;
    }

    public YaMessageForm mapMessage(YaMessageDto dto) {
        YaWeatherForm fact = mapFact(dto.getFact());
        List<YaWeatherForm> parts = mapParts(dto.getForecast());
        return new YaMessageForm(fact, parts);
    }

}
