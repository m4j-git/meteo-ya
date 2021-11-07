/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

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

public enum YaConditional {

    CLEAR("clear"), OVERCAST("overcast"), CLOUDY("cloudy"), PARTLY_CLOUDY("partly-cloudy"), PARTLY_CLOUDY_AND_LIGHT_RAIN(
            "partly-cloudy-and-light-rain"), OVERCAST_AND_LIGHT_RAIN("overcast-and-light-rain");

    private final String value;

    YaConditional(String value) {
        this.value = value;
    }

    public static YaConditional fromString(String value) {
        for (YaConditional v : values()) {
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
