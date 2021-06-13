/*
 * Copyright (c) 2002-2021 meteo@woodapiary.com
 */
package com.woodapiary.meteo.ya.requester;

import com.woodapiary.meteo.ya.model.YaMessageDto;

import java.net.URI;

public interface YaMessageClient {

    YaMessageDto request(URI uri);

}
