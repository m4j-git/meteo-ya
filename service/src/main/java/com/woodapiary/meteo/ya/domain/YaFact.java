/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "ya_fact")
public class YaFact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private Short feelsLike;

    /**
     * temp_water
     * Температура воды (°C).
     * Параметр возвращается для населенных пунктов, где данная информация актуальна.
     * Число null
     */
    private Short tempWater;

    /**
     * icon
     * Код иконки погоды.
     * Иконка доступна по адресу https://yastatic.net/weather/i/icons/blueye/color/svg/<значение из поля icon>.svg.
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
    private Short windSpeed;

    /**
     * wind_gust
     * Скорость порывов ветра (в м/с).
     * Число 0.6 .. 20.1
     */
    @NotNull
    private Short windGust;

    /**
     * wind_dir
     * Направление ветра.
     * Строка
     */
    @NotNull
    @Column(length = 3)
    private String windDir;

    /**
     * pressure_mm
     * Давление (в мм рт. ст.).
     * Число 731 ... 774
     */
    @NotNull
    private Short pressureMm;

    /**
     * pressure_pa
     * Давление (в гектопаскалях).
     * Число 974 ... 1031
     */
    @NotNull
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
    private OffsetDateTime obsTime;

    @NotNull
    @OneToOne
    @JoinColumn(name = "message_id", referencedColumnName = "message_id", nullable = false, unique = true, updatable = false)
    private YaMessage message;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof YaFact)) {
            return false;
        }
        YaFact other = (YaFact) o;
        return factId != null &&
                factId.equals(other.getFactId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(factId);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
