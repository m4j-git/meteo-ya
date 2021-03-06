/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.requester;

import java.io.IOException;
import java.net.URI;

import ru.m4j.meteo.ya.model.YaMessageDto;

public interface YaMessageClient {

    YaMessageDto request(URI uri) throws IOException;

}
