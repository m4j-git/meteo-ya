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
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "ya_part")
public class YaPart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Column(length = 8)
    private String partName;

    /**
     * temp_min
     * Минимальная температура для времени суток (°C).
     * Число
     */
    @NotNull
    private Short tempMin;

    /**
     * temp_max
     * Максимальная температура для времени суток (°C).
     * Число
     */
    @NotNull
    private Short tempMax;

    /**
     * temp_avg
     * Средняя температура для времени суток (°C).
     * Число
     */
    @NotNull
    private Short tempAvg;

    /**
     * feels_like
     * Ощущаемая температура (°C).
     * Число
     */
    @NotNull
    private Short feelsLike;

    /**
     * icon
     * Код иконки погоды.
     * Иконка доступна по адресу  https://yastatic.net/weather/i/icons/blueye/color/svg/<значение из поля icon>.svg.
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
    private Short windSpeed;

    /**
     * wind_gust
     * Скорость порывов ветра (в м/с).
     * Число
     */
    @NotNull
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
     **/
    @NotNull
    @Column(length = 3)
    private String windDir;

    /**
     * pressure_mm
     * Давление (в мм рт. ст.).
     * Число
     */
    @NotNull
    private Short pressureMm;

    /**
     * pressure_pa
     * Давление (в гектопаскалях).
     * Число
     */
    @NotNull
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
    private Short precMm;

    /**
     * prec_period
     * Прогнозируемый период осадков (в минутах).
     * Число 360 ... 360
     */
    @NotNull
    private Short precPeriod;

    /**
     * prec_prob
     * Вероятность выпадения осадков.
     * Число 0 ... 100
     */
    @NotNull
    private Short precProb;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "forecast_id", nullable = false, updatable = false)
    private YaForecast forecast;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof YaPart)) {
            return false;
        }
        YaPart other = (YaPart) o;
        return partId != null &&
                partId.equals(other.getPartId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(partId);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
