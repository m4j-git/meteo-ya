/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.requester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.m4j.meteo.ya.model.YaMessageDto;

@ConditionalOnProperty(name = "meteo.client", havingValue = "http")
@Component
public class YaMessageClientHttp implements YaMessageClient {

    private final ObjectMapper jacksonMapper;

    @Value("${YANDEX_API_KEY}")
    private String apiKey;

    public YaMessageClientHttp(ObjectMapper jacksonMapper) {
        this.jacksonMapper = jacksonMapper;
    }


    @Override
    public YaMessageDto request(URI uri) {
        try {
            URLConnection connection = uri.toURL().openConnection();
            connection.setRequestProperty("X-Yandex-API-Key", apiKey);
            try (InputStream is = connection.getInputStream();
                 BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                final YaMessageDto owDto = jacksonMapper.readValue(rd, YaMessageDto.class);
                rd.close();
                return owDto;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
