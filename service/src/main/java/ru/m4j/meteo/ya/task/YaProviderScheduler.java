/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.task;

import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.m4j.meteo.ya.model.GeonameDto;
import ru.m4j.meteo.ya.requester.YaMessageRequester;
import ru.m4j.meteo.ya.service.YaGeonameService;

@Service
@ConditionalOnProperty(name = "meteo.scheduling.enabled", havingValue = "true")
public class YaProviderScheduler {

    private static final int mFixedRate = 3600 * 2;

    private final YaMessageRequester requester;
    private final YaGeonameService geo;

    public YaProviderScheduler(YaMessageRequester requester, YaGeonameService geo) {
        this.requester = requester;
        this.geo = geo;
    }

    @Scheduled(fixedRate = 1000 * mFixedRate, initialDelay = 1000)
    public void run() {
        List<GeonameDto> gns = geo.requestGeonames();
        for (final GeonameDto gn : gns) {
            requester.requestProvider(gn);
        }
    }

}
