/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.requester;

import java.net.URI;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import ru.m4j.meteo.ya.model.YaMessageDto;

@Slf4j
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

    @PostConstruct
    void init() {
        log.info(this.getClass().getCanonicalName() + " inited");
    }
}
