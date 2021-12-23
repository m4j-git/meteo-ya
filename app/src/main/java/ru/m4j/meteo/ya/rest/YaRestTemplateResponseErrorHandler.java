/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.rest;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class YaRestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return ((httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR)
            || (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR));
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        log.error(String.format("%s, %s", httpResponse.getStatusCode(), httpResponse.getStatusText()));
    }
}
