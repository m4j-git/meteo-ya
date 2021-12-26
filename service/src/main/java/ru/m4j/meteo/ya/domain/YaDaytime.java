/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

/**
 * daytime
 * Светлое или темное время суток.
 * Возможные значения:
 * «d» — светлое время суток.
 * «n» — темное время суток.
 * Строка
 */

public enum YaDaytime {

    D("d"), N("n");

    private final String value;

    YaDaytime(String value) {
        this.value = value;
    }

    public static YaDaytime fromString(String value) {
        for (YaDaytime v : values()) {
            if (v.getValue().equalsIgnoreCase(value)) {
                return v;
            }
        }
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
