/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ya.form.YaMessageForm;
import ru.m4j.meteo.ya.form.YaWeatherFormMapper;
import ru.m4j.meteo.ya.model.LocationDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.service.LocationService;
import ru.m4j.meteo.ya.service.YaMessageService;

@Slf4j
@Controller
@RequestMapping("/")
//@SessionAttributes("locations")
public class YaController {

    private final YaMessageService service;
    private final LocationService locationService;
    private final YaWeatherFormMapper mapper;

    public YaController(YaMessageService service, LocationService locationService, YaWeatherFormMapper mapper) {
        this.service = service;
        this.locationService = locationService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public String showMessagePage(Model model, @RequestParam("geonameId") Integer geonameId) {
        log.info("request:" + geonameId);
        YaMessageDto dto = service.getLastMessage(geonameId);
        YaMessageForm form = mapper.mapMessage(dto);
        model.addAttribute("weather", form);
        return "ya";
    }

    @ModelAttribute("locations")
    public List<LocationDto> getLocations() {
        return locationService.requestLocations();
    }

}
