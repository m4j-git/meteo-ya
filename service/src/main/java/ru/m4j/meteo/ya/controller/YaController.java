/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

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

    public YaController(YaMessageService service, YaLocationService locationService) {
        this.service = service;
        this.locationService = locationService;
    }

    @GetMapping("/")
    public String showFactPage(Model model, @ModelAttribute("location") LocationDto location) {
        model.addAttribute("admin-done", SecurityContextHolder.getContext().getAuthentication().getName());
        YaMessageDto data = service.getLastMessage(location.getGeonameId());
        if (data == null) {
            return "index2";
        }
        model.addAttribute("weather", data);
        return "index";
    }

    @ModelAttribute("location")
    public LocationDto getGeoname() {
        return locationService.requestLocations().get(0);
    }

}
