/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ya_forecast")
public class YaForecast implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long forecastId;

    /**
     * date
     * Дата прогноза в формате ГГГГ-ММ-ДД.
     * Строка
     */
    @NotNull
    private LocalDate date;

    /**
     * date_ts
     * Дата прогноза в формате Unixtime.
     * Число
     */
    @NotNull
    private OffsetDateTime dateTs;

    /**
     * week
     * Порядковый номер недели.
     * Число
     */
    @NotNull
    private Byte week;

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
     */
    @NotNull
    private Byte moonCode;

    /**
     * moon_text
     * Текстовый код для фазы Луны.
     */
    @NotNull
    private String moonText;

    @Setter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "partId", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH}, orphanRemoval = true)
    private List<YaPart> parts = new ArrayList<>();

    @NotNull
    @OneToOne
    @JoinColumn(name = "message_id", referencedColumnName = "message_id", nullable = false, unique = true, updatable = false)
    private YaMessage message;


    public void addParts(final Iterable<YaPart> parts) {
        parts.forEach(this::addPart);
    }

    public YaPart addPart(final YaPart part) {
        getParts().add(part);
        part.setForecast(this);
        return part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof YaForecast)) {
            return false;
        }
        YaForecast other = (YaForecast) o;
        return forecastId != null &&
                forecastId.equals(other.getForecastId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(forecastId);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
