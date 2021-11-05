/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class YaPartDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * part_name
     * Название времени суток.
     * Возможные значения:
     * night — ночь.
     * morning — утро.
     * day — день.
     * evening — вечер.
     * Строка.
     */
    @JsonProperty("part_name")
    private String partName;
    /**
     * temp_min
     * Минимальная температура для времени суток (°C).
     * Число
     */
    @JsonProperty("temp_min")
    private Integer tempMin;
    /**
     * temp_max
     * Максимальная температура для времени суток (°C).
     * Число
     */
    @JsonProperty("temp_max")
    private Integer tempMax;
    /**
     * temp_avg
     * Средняя температура для времени суток (°C).
     * Число
     */
    @JsonProperty("temp_avg")
    private Integer tempAvg;
    /**
     * feels_like
     * Ощущаемая температура (°C).
     * Число
     */
    @JsonProperty("feels_like")
    private Integer feelsLike;
    /**
     * icon
     * Код иконки погоды.
     * Иконка доступна по адресу  https://yastatic.net/weather/i/icons/blueye/color/svg/<значение из поля icon>.svg.
     * Строка
     */
    private String icon;
    /**
     * condition
     * Код расшифровки погодного описания.
     * Возможные значения:
     * clear — ясно.
     * partly-cloudy — малооблачно.
     * cloudy — облачно с прояснениями.
     * overcast — пасмурно.
     * partly-cloudy-and-light-rain — небольшой дождь.
     * partly-cloudy-and-rain —дождь.
     * overcast-and-rain — сильный дождь.
     * overcast-thunderstorms-with-rain — сильный дождь, гроза.
     * cloudy-and-light-rain — небольшой дождь.
     * overcast-and-light-rain — небольшой дождь.
     * cloudy-and-rain — дождь.
     * overcast-and-wet-snow — дождь со снегом.
     * partly-cloudy-and-light-snow — небольшой снег.
     * partly-cloudy-and-snow —снег.
     * overcast-and-snow — снегопад.
     * cloudy-and-light-snow — небольшой снег.
     * overcast-and-light-snow — небольшой снег.
     * cloudy-and-snow — снег.
     * Строка
     */
    @JsonProperty("condition")
    private String condition;
    /**
     * daytime
     * Светлое или темное время суток.
     * Возможные значения:
     * «d» — светлое время суток.
     * «n» — темное время суток.
     * Строка
     */
    private String daytime;
    /**
     * polar
     * Признак полярного дня или ночи.
     * Логический
     */
    private Boolean polar;
    /**
     * wind_speed
     * Скорость ветра (в м/с).
     * Число
     */
    @JsonProperty("wind_speed")
    private Double windSpeed;
    /**
     * wind_gust
     * Скорость порывов ветра (в м/с).
     * Число
     */
    @JsonProperty("wind_gust")
    private Double windGust;
    /**
     * wind_dir
     * Направление ветра.
     * Возможные значения:
     * «nw» — северо-западное.
     * «n» — северное.
     * «ne» — северо-восточное.
     * «e»— восточное.
     * «se» — юго-восточное.
     * «s» — южное.
     * «sw» — юго-западное.
     * «w» — западное.
     * «с» — штиль.
     * Строка
     */
    @JsonProperty("wind_dir")
    private String windDir;
    /**
     * pressure_mm
     * Давление (в мм рт. ст.).
     * Число
     */
    @JsonProperty("pressure_mm")
    private Integer pressureMm;
    /**
     * pressure_pa
     * Давление (в гектопаскалях).
     * Число
     */
    @JsonProperty("pressure_pa")
    private Integer pressurePa;
    /**
     * humidity
     * Влажность воздуха (в процентах).
     * Число
     */
    private Integer humidity;
    /**
     * prec_mm
     * Прогнозируемое количество осадков (в мм).
     * Число
     */
    @JsonProperty("prec_mm")
    private Double precMm;
    /**
     * prec_period
     * Прогнозируемый период осадков (в минутах).
     * Число
     */
    @JsonProperty("prec_period")
    private Integer precPeriod;
    /**
     * prec_prob
     * Вероятность выпадения осадков.
     * Число
     */
    @JsonProperty("prec_prob")
    private Integer precProb;
    
    @JsonProperty("part_id")
    private Long partId;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
