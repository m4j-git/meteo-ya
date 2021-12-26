/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ya_message")
@EntityListeners(AuditingEntityListener.class)
public class YaMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    @EqualsAndHashCode.Include
    private Long messageId;

    @CreatedDate
    @Column(name = "created_on", nullable = false, updatable = false, columnDefinition = "timestamp")
    private LocalDateTime createdOn;

    @NotNull
    @Column(name = "message_uuid", nullable = false, updatable = false, unique = true, columnDefinition = "varbinary")
    private UUID messageUuid;

    @NotNull
    @Column(name = "geoname_id", nullable = false, updatable = false)
    private Integer geonameId;

    /**
     * now
     * Время сервера в формате unixtime.
     * Число
     */
    @NotNull
    private OffsetDateTime now;

    /**
     * now_dt
     * Время сервера в UTC.
     * Строка
     */
    @NotNull
    @Column(name = "now_dt")
    private OffsetDateTime nowDt;

    @Setter(AccessLevel.NONE)
    @OneToOne(mappedBy = "message", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH }, orphanRemoval = true)
    private YaFact fact;

    @Setter(AccessLevel.NONE)
    @OneToOne(mappedBy = "message", cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH }, orphanRemoval = true)
    private YaForecast forecast;

    public void addFact(final YaFact fact) {
        this.fact = fact;
        fact.setMessage(this);
    }

    public void addForecast(final YaForecast forecast) {
        this.forecast = forecast;
        forecast.setMessage(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
