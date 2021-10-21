/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
public class YaFactDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * temp
     * Температура (°C)
     * Число
     */
    private Integer temp;
    /**
     * feels_like
     * Ощущаемая температура (°C).
     * Число
     */
    @JsonProperty("feels_like")
    private Integer feelsLike;
    /**
     * temp_water
     * Температура воды (°C).
     * Параметр возвращается для населенных пунктов, где данная информация актуальна.
     * Число
     */
    @JsonProperty("temp_water")
    private Integer tempWater;
    /**
     * icon
     * Код иконки погоды.
     * Иконка доступна по адресу https://yastatic.net/weather/i/icons/blueye/color/svg/<значение из поля icon>.svg.
     * Строка
     */
    private String icon;
    /**
     * condition
     * Код расшифровки погодного описания.
     * Возможные значения:
     * clear — ясно.
     * partly-cloudy — малооблачно.
     * cloudy —облачно с прояснениями.
     * overcast — пасмурно.
     * partly-cloudy-and-light-rain — небольшой дождь.
     * partly-cloudy-and-rain — дождь.
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
     * season
     * Время года в данном населенном пункте.
     * Возможные значения:
     * «summer» — лето.
     * «autumn» — осень.
     * «winter» — зима.
     * «spring» — весна.
     * Строка
     */
    private String season;
    /**
     * obs_time
     * Время замера погодных данных в формате Unixtime.
     * Число
     */
    @JsonProperty("obs_time")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Instant obsTime;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
