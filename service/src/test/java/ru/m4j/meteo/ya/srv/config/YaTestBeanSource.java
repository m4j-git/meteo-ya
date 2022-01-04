/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.srv.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.m4j.meteo.ya.model.YaMessageDto;

@Component
public class YaTestBeanSource {

    private static final String TEST_DATA_FILE = "ya_v1.json";

    @Autowired
    private ObjectMapper jacksonMapper;

    public YaMessageDto readJson() throws IOException {
        return jacksonMapper.readValue((new ClassPathResource("data/" + TEST_DATA_FILE)).getInputStream(), YaMessageDto.class);

    }

}
