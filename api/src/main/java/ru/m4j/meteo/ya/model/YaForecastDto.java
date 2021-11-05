/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class YaForecastDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * date
     * Дата прогноза в формате ГГГГ-ММ-ДД.
     * Строка
     */
    private LocalDate date;
    /**
     * date_ts
     * Дата прогноза в формате Unixtime.
     * Число
     */
    @JsonProperty("date_ts")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Instant dateTs;
    /**
     * week
     * Порядковый номер недели.
     * Число
     */
    private Integer week;
    /**
     * sunrise
     * Время восхода Солнца,локальное время(может отсутствовать для полярных регионов).
     * Строка
     */
    private LocalTime sunrise;
    /**
     * sunset
     * Время заката Солнца,локальное время(может отсутствовать для полярных регионов).
     * Строка
     */
    private LocalTime sunset;
    /**
     * moon_code
     * Код фазы Луны.
     * Возможные значения:
     * 0—полнолуние.
     * 1-3—убывающая Луна.
     * 4—последняя четверть.
     * 5-7—убывающая Луна.
     * 8—новолуние.
     * 9-11—растущая Луна.
     * 12—первая четверть.
     * 13-15—растущая Луна.
     * Число
     */
    @JsonProperty("moon_code")
    private Integer moonCode;
    /**
     * moon_text
     * Текстовый код для фазы Луны.
     * Возможные значения:
     * full-moon—полнолуние.
     * decreasing-moon—убывающая Луна.
     * last-quarter—последняя четверть.
     * new-moon—новолуние.
     * growing-moon—растущая Луна.
     * first-quarter—первая четверть.
     * Строка
     */
    @JsonProperty("moon_text")
    private String moonText;
    /**
     * parts
     * Прогнозы по времени суток.
     * Содержит следующие поля:
     * part_name
     * temp_min
     * temp_max
     * temp_avg
     * feels_like
     * icon
     * condition
     * daytime
     * polar
     * wind_speed
     * wind_gust
     * wind_dir
     * pressure_mm
     * pressure_pa
     * humidity
     * prec_mm
     * prec_period
     * prec_prob
     * Все прогнозы погоды на время суток имеют одинаковый набор полей.
     * Ответ содержит прогноз на 2 ближайших периода.
     * Объект
     */
    private List<YaPartDto> parts = null;
    
    @JsonProperty("forecast_id")
    private Long forecastId;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
