package ru.m4j.meteo.ya.service;

import java.util.List;

import ru.m4j.meteo.ya.model.GeonameDto;

public interface YaGeonameService {

    List<GeonameDto> requestGeonames();

}