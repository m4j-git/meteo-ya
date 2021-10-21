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
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class YaMessageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * message_uuid
     * Уникальный идентификатор
     * Строка
     */
    @JsonProperty("message_uuid")
    private UUID messageUuid;
    /**
     * created_on
     * Временная метка запроса провайдера
     * Строка
     */
    @JsonProperty("created_on")
    private Instant createdOn;
    /**
     * now
     * Время сервера в формате unixtime.
     * Число
     */
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Instant now;
    /**
     * now_dt
     * Время сервера в UTC.
     * Строка
     */
    @JsonProperty("now_dt")
    private Instant nowDt;
    /**
     * info
     * Объект информации о населенном пункте.
     * Объект
     */
    private YaInfoDto info;
    /**
     * fact
     * Объект фактической информации о погоде.
     * Объект
     */
    private YaFactDto fact;
    /**
     * forecast
     * Объект прогнозной информации о погоде.
     * Объект
     */
    private YaForecastDto forecast;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
