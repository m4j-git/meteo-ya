package ru.m4j.meteo.ya.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.m4j.meteo.ya.model.GeonameDto;

@Service
public class YaGeonameServiceImpl implements YaGeonameService {

    @Override
    public List<GeonameDto> requestGeonames() {
        return List.of(new GeonameDto(1, "Moscow", 55.75, 37.6), new GeonameDto(2, "Shilovo", 54.571705, 41.083740),
                new GeonameDto(3, "Kaliningrad", 54.710157, 20.510137), new GeonameDto(4, "Magadan", 59.5638, 150.803));
    }

}
