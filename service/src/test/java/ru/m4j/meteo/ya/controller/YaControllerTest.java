/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import ru.m4j.meteo.ya.config.YaTestBeanSource;
import ru.m4j.meteo.ya.config.YaWebSecurityTestConfig;
import ru.m4j.meteo.ya.form.YaWeatherFormMapper;
import ru.m4j.meteo.ya.model.LocationDto;
import ru.m4j.meteo.ya.service.YaLocationService;
import ru.m4j.meteo.ya.service.YaMessageService;

@ContextConfiguration(classes = { YaWeatherFormMapper.class, YaWebSecurityTestConfig.class, YaTestBeanSource.class })
@WebMvcTest(controllers = { YaController.class })
class YaControllerTest {

    private final String geonameId = "1";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private YaMessageService service;
    @Autowired
    private YaTestBeanSource src;
    @MockBean
    private YaLocationService locationService;

    @Test
    void showMessagePage_test() throws Exception {
        when(locationService.requestLocations()).thenReturn(List.of(new LocationDto(1, null, null, null)));
        given(service.getLastMessage(geonameId)).willReturn(src.readJson());
        mockMvc.perform(get("/")
            .param("geonameId", "1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().hasNoErrors())
            .andExpect(model().attributeExists("weather"))
            .andExpect(view().name("index"));
    }

}
