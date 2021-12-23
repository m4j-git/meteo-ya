/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ya_part")
public class YaPart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "part_id")
    private Long partId;

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
    @NotNull
    @Column(name = "part_name", length = 8)
    private String partName;

    /**
     * temp_min
     * Минимальная температура для времени суток (°C).
     * Число
     */
    @NotNull
    @Column(name = "temp_min")
    private Short tempMin;

    /**
     * temp_max
     * Максимальная температура для времени суток (°C).
     * Число
     */
    @NotNull
    @Column(name = "temp_max")
    private Short tempMax;

    /**
     * temp_avg
     * Средняя температура для времени суток (°C).
     * Число
     */
    @NotNull
    @Column(name = "temp_avg")
    private Short tempAvg;

    /**
     * feels_like
     * Ощущаемая температура (°C).
     * Число
     */
    @NotNull
    @Column(name = "feels_like")
    private Short feelsLike;

    /**
     * icon
     * Код иконки погоды.
     * Иконка доступна по адресу  https://yastatic.net/weather/i/icons/blueye/color/svg/значение из поля icon.svg.
     * Строка
     */
    @NotNull
    private String icon;

    /**
     * condition
     * Код расшифровки погодного описания.
     **/
    @NotNull
    @Column(name = "condition_")
    private String condition;

    /**
     * daytime
     * Светлое или темное время суток.
     * Возможные значения:
     * «d» — светлое время суток.
     * «n» — темное время суток.
     * Строка
     */
    @NotNull
    @Column(length = 1)
    private String daytime;

    /**
     * polar
     * Признак полярного дня или ночи.
     * Логический
     */
    @NotNull
    private Boolean polar;

    /**
     * wind_speed
     * Скорость ветра (в м/с).
     * Число
     */
    @NotNull
    @Column(name = "wind_speed")
    private Short windSpeed;

    /**
     * wind_gust
     * Скорость порывов ветра (в м/с).
     * Число
     */
    @NotNull
    @Column(name = "wind_gust")
    private Short windGust;

    /**
     * wind_dir
     * Направление ветра.
     * se
     * e
     * s
     * sw
     * w
     * n
     * nw
     * ne
     */
    @NotNull
    @Column(name = "wind_dir", length = 3)
    private String windDir;

    /**
     * pressure_mm
     * Давление (в мм рт. ст.).
     * Число
     */
    @NotNull
    @Column(name = "pressure_mm")
    private Short pressureMm;

    /**
     * pressure_pa
     * Давление (в гектопаскалях).
     * Число
     */
    @NotNull
    @Column(name = "pressure_pa")
    private Short pressurePa;

    /**
     * humidity
     * Влажность воздуха (в процентах).
     * Число
     */
    @NotNull
    private Short humidity;

    /**
     * prec_mm
     * Прогнозируемое количество осадков (в мм).
     * Число 0 ... 28.6
     */
    @NotNull
    @Column(name = "prec_mm")
    private Short precMm;

    /**
     * prec_period
     * Прогнозируемый период осадков (в минутах).
     * Число 360 ... 360
     */
    @NotNull
    @Column(name = "prec_period")
    private Short precPeriod;

    /**
     * prec_prob
     * Вероятность выпадения осадков.
     * Число 0 ... 100
     */
    @NotNull
    @Column(name = "prec_prob")
    private Short precProb;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "forecast_id", nullable = false, updatable = false)
    private YaForecast forecast;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
