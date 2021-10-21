/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.requester;

import ru.m4j.meteo.ya.model.YaMessageDto;

import java.net.URI;

public interface YaMessageClient {

    YaMessageDto request(URI uri);

}
