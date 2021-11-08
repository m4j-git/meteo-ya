/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import java.net.URL;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ru.m4j.meteo.ya.model.LocationDto;
import ru.m4j.meteo.ya.model.LocationsDto;

@Service
public class YaLocationServiceImpl implements YaLocationService {

    @Override
    public List<LocationDto> requestLocations() {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            URL resource = YaLocationService.class.getClassLoader().getResource("data/locations.xml");
            LocationsDto locations = xmlMapper.readValue(Paths.get(resource.toURI()).toFile(), LocationsDto.class);
            return locations.getLocations();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
