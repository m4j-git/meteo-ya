/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.api;

import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/v1/ya")
public interface YaRestResource {

    @ApiOperation(notes = "Yandex messages", value = "get list of weather messages")
    @GetMapping("/messages")
    List<YaMessageDto> getMessages(@RequestParam Integer geonameId,
                                   @RequestParam(required = false) String dateFrom, @RequestParam(required = false) String dateTo);

    @ApiOperation(notes = "Yandex messages", value = "get weather message")
    @GetMapping("/messages/{uuid}")
    YaMessageDto getMessage(@PathVariable(value = "uuid") String uuid);

    @ApiOperation(notes = "Yandex messages", value = "get last weather message")
    @GetMapping("/messages/last")
    YaMessageDto getLastMessage(@RequestParam Integer geonameId);

    @ApiOperation(notes = "Yandex messages", value = "get fact weather messages")
    @GetMapping("/messages/facts")
    List<YaFactDto> getFacts(@RequestParam Integer geonameId,
                             @RequestParam(required = false) String dateFrom, @RequestParam(required = false) String dateTo);
}
