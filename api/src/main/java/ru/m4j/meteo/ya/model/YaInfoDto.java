/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class YaInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * lat
     * Широта (в градусах).
     * Число
     */
    private Double lat;
    /**
     * lon
     * Долгота (в градусах).
     * Число
     */
    private Double lon;
    /**
     * url
     * Страница населенного пункта на сайте Яндекс.Погода.
     * Строка
     */
    private String url;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
