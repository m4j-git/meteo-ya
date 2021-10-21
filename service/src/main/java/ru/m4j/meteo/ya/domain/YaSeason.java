/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

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
public enum YaSeason {

    SUMMER("summer"),
    AUTUMN("autumn"),
    WINTER("winter"),
    SPRING("spring");

    private final String value;

    YaSeason(String value) {
        this.value = value;
    }

    public static YaSeason fromString(String value) {
        for (YaSeason v : values())
            if (v.getValue().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
