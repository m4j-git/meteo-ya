/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.service;

import java.util.Collections;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ru.m4j.meteo.ya.model.LocationDto;
import ru.m4j.meteo.ya.model.LocationsDto;

@Service
public class YaLocationServiceImpl implements YaLocationService {

    private final ResourceLoader resourceLoader;

    public YaLocationServiceImpl(ResourceLoader resourceLoader) {
        super();
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<LocationDto> requestLocations() {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            final Resource resource = resourceLoader.getResource("classpath:data/locations.xml");
            LocationsDto locations = xmlMapper.readValue(resource.getInputStream(), LocationsDto.class);
            return locations.getLocations();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
