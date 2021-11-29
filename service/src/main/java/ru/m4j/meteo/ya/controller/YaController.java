/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import ru.m4j.meteo.ya.form.YaMessageForm;
import ru.m4j.meteo.ya.form.YaWeatherFormMapper;
import ru.m4j.meteo.ya.model.LocationDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.service.YaLocationService;
import ru.m4j.meteo.ya.service.YaMessageService;

@Controller
@RequestMapping("/")
@SessionAttributes("location")
public class YaController {

    private final YaMessageService service;
    private final YaLocationService locationService;
    private final YaWeatherFormMapper mapper;

    public YaController(YaMessageService service, YaLocationService locationService, YaWeatherFormMapper mapper) {
        this.service = service;
        this.locationService = locationService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public String showFactPage(Model model, @ModelAttribute("location") LocationDto location) {
        YaMessageDto dto = service.getLastMessage(location.getGeonameId());
        YaMessageForm form = mapper.mapMessage(dto);
        model.addAttribute("weather", form);
        return "index";
    }

    @ModelAttribute("location")
    public LocationDto getGeoname() {
        return locationService.requestLocations().get(0);
    }

}
