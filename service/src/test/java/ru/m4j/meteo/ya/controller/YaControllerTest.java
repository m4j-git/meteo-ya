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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.share.app.GlobalConstants;
import ru.m4j.meteo.ya.YaTestApplication;
import ru.m4j.meteo.ya.model.LocationDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.service.YaLocationService;
import ru.m4j.meteo.ya.service.YaMessageService;

@WebMvcTest(controllers = { YaController.class })
@ContextConfiguration(classes = YaTestApplication.class)
@Slf4j
class YaControllerTest {

    private final Integer geonameId = 1;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private YaMessageService service;
    private static final String TEST_DATA_FILE = "ow_onecall.json";
    @Autowired
    private ObjectMapper jacksonMapper;
    @MockBean
    private YaLocationService locationService;

    @Test
    void showMessagePage_test() throws Exception {
        when(locationService.requestLocations()).thenReturn(List.of(new LocationDto(1, null, null, null)));
        given(service.getLastMessage(geonameId)).willReturn(readJson());
        mockMvc.perform(get("/")
            .param("geonameId", "1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(model().hasNoErrors())
            .andExpect(model().attributeExists("weather"))
            .andExpect(view().name("index"));
    }

    private YaMessageDto readJson() throws IOException {
        final FileInputStream fis = new FileInputStream(GlobalConstants.TEST_DATA_PATH + TEST_DATA_FILE);
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            YaMessageDto dto = jacksonMapper.readValue(rd, YaMessageDto.class);
            log.debug(dto.toString());
            return dto;
        }
    }

}
