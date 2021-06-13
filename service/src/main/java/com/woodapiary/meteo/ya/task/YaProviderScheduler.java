/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.task;

import com.woodapiary.meteo.ya.model.GeonameDto;
import com.woodapiary.meteo.ya.requester.YaMessageRequester;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(name = "meteo.scheduling.enabled", havingValue = "true")
public class YaProviderScheduler {

    private static final int mFixedRate = 3600 * 4;

    private final YaMessageRequester requester;

    public YaProviderScheduler(YaMessageRequester requester) {
        this.requester = requester;
    }

    @Scheduled(fixedRate = 1000 * mFixedRate, initialDelay = 1000)
    public void run() {
        List<GeonameDto> gns = requestGeonames();
        for (final GeonameDto gn : gns) {
            requester.requestProvider(gn);
        }
    }

    //FIXME via service
    private List<GeonameDto> requestGeonames() {
        return List.of(new GeonameDto(1, "Moscow", 55.75, 37.6),
                new GeonameDto(2, "Shilovo", 54.571705, 41.083740),
                new GeonameDto(3, "Kaliningrad", 54.710157, 20.510137));
    }


}
