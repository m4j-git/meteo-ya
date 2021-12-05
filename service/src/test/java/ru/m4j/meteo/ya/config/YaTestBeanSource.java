/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.config;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.share.app.GlobalConstants;
import ru.m4j.meteo.ya.model.YaMessageDto;

@Slf4j
@Component
public class YaTestBeanSource {

    private static final String TEST_DATA_FILE = "ya_v1.json";

    @Autowired
    private ObjectMapper jacksonMapper;

    public YaMessageDto readJson() throws IOException {
        final FileInputStream fis = new FileInputStream(GlobalConstants.TEST_DATA_PATH + TEST_DATA_FILE);
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))) {
            YaMessageDto dto = jacksonMapper.readValue(rd, YaMessageDto.class);
            log.debug("read from json " + dto);
            return dto;
        }
    }

}
