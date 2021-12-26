/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.requester;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ru.m4j.meteo.ya.model.YaMessageDto;

@ConditionalOnProperty(name = "meteo.provider.type", havingValue = "resttemplate", matchIfMissing = true)
@Component
public class YaMessageClientRestTemplate implements YaMessageClient {

    private final RestTemplate restTemplate;

    @Value("${YANDEX_API_KEY}")
    private String apiKey;

    public YaMessageClientRestTemplate(RestTemplateBuilder rtBuilder) {
        this.restTemplate = rtBuilder.additionalInterceptors((request, body, execution) -> {
            request.getHeaders().add("X-Yandex-API-Key", apiKey);
            return execution.execute(request, body);
        }).build();
    }

    @Override
    public YaMessageDto request(URI uri) {
        return restTemplate.getForObject(uri, YaMessageDto.class);
    }

}
