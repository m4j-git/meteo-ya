/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ya_fact")
public class YaFact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "fact_id")
    private Long factId;

    /**
     * temp
     * Температура (°C)
     * Число -27 ... 26
     */
    @NotNull
    private Short temp;

    /**
     * feels_like
     * Ощущаемая температура (°C).
     * Число -32 ... 22
     */
    @NotNull
    @Column(name = "feels_like")
    private Short feelsLike;

    /**
     * temp_water
     * Температура воды (°C).
     * Параметр возвращается для населенных пунктов, где данная информация актуальна.
     * Число null
     */
    @Column(name = "temp_water")
    private Short tempWater;

    /**
     * icon
     * Код иконки погоды.
     * Иконка доступна по адресу https://yastatic.net/weather/i/icons/blueye/color/svg/значение из поля icon.svg.
     * Строка
     * skc_d
     * ovc
     * bkn_d
     * skc_n
     * bkn_n
     * bkn_-ra_n
     * ovc_-ra
     * ovc_+ra
     * ovc_ra
     * ovc_ra_sn
     * bkn_-ra_d
     * bkn_ra_n
     * bkn_+ra_d
     * ovc_-sn
     * ovc_sn
     * bkn_sn_n
     * bkn_-sn_n
     * bkn_-sn_d
     * ovc_+sn
     * bkn_sn_d
     * bkn_ra_d
     * bkn_-ra
     */
    @NotNull
    private String icon;

    /**
     * condition
     * Код расшифровки погодного описания.
     * clear
     * overcast
     * cloudy
     * partly-cloudy
     * partly-cloudy-and-light-rain
     * overcast-and-light-rain
     * overcast-and-rain
     * cloudy-and-rain
     * overcast-and-wet-snow
     * cloudy-and-light-rain
     * overcast-and-light-snow
     * cloudy-and-snow
     * partly-cloudy-and-snow
     * partly-cloudy-and-light-snow
     * cloudy-and-light-snow
     * overcast-and-snow
     * partly-cloudy-and-rain
     */
    @NotNull
    @Column(name = "condition_")
    private String condition;

    /**
     * wind_speed
     * Скорость ветра (в м/с).
     * Число 0 ... 11.0
     */
    @NotNull
    @Column(name = "wind_speed")
    private Short windSpeed;

    /**
     * wind_gust
     * Скорость порывов ветра (в м/с).
     * Число 0.6 .. 20.1
     */
    @NotNull
    @Column(name = "wind_gust")
    private Short windGust;

    /**
     * wind_dir
     * Направление ветра.
     * Строка
     */
    @NotNull
    @Column(name = "wind_dir", length = 3)
    private String windDir;

    /**
     * pressure_mm
     * Давление (в мм рт. ст.).
     * Число 731 ... 774
     */
    @NotNull
    @Column(name = "pressure_mm")
    private Short pressureMm;

    /**
     * pressure_pa
     * Давление (в гектопаскалях).
     * Число 974 ... 1031
     */
    @NotNull
    @Column(name = "pressure_pa")
    private Short pressurePa;

    /**
     * humidity
     * Влажность воздуха (в процентах).
     * Число 20 ... 100
     */
    @NotNull
    private Short humidity;

    /**
     * daytime
     * Светлое или темное время суток.
     * Возможные значения:
     * «d» — светлое время суток.
     * «n» — темное время суток.
     * Строка
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private YaDaytime daytime;

    /**
     * polar
     * Признак полярного дня или ночи.
     * Логический 0/1
     */
    @NotNull
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
    @NotNull
    @Enumerated(EnumType.STRING)
    private YaSeason season;

    /**
     * obs_time
     * Время замера погодных данных в формате Unixtime.
     * Число
     */
    @NotNull
    @Column(name = "obs_time")
    private OffsetDateTime obsTime;

    @NotNull
    @OneToOne
    @JoinColumn(name = "message_id", referencedColumnName = "message_id", nullable = false, unique = true, updatable = false)
    private YaMessage message;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
