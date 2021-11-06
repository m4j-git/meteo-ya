/*
 * Copyright (c) 2002-2021 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.mapper;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import ru.m4j.meteo.ya.domain.YaDaytime;
import ru.m4j.meteo.ya.domain.YaFact;
import ru.m4j.meteo.ya.domain.YaForecast;
import ru.m4j.meteo.ya.domain.YaMessage;
import ru.m4j.meteo.ya.domain.YaPart;
import ru.m4j.meteo.ya.domain.YaSeason;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaForecastDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.model.YaPartDto;

@Component
public class YaMessageDtoModelMapper {

    private final ModelMapper modelMapper;

    private final Converter<List<YaPart>, List<YaPartDto>> partListDtoFromPartList = new AbstractConverter<>() {
        @Override
        protected List<YaPartDto> convert(final List<YaPart> source) {
            if (source == null) {
                return null;
            }
            return partListDtoFromPartList(source);
        }
    };

    private final Converter<List<YaPartDto>, List<YaPart>> partListDtoToPartList = new AbstractConverter<>() {
        @Override
        protected List<YaPart> convert(final List<YaPartDto> source) {
            if (source == null) {
                return null;
            }
            return partListDtoToPartList(source);
        }
    };

    public YaMessageDtoModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        modelMapper.createTypeMap(YaMessageDto.class, YaMessage.class).addMapping(YaMessageDto::getFact, YaMessage::addFact).addMapping(YaMessageDto::getForecast, YaMessage::addForecast);

        modelMapper.createTypeMap(YaFactDto.class, YaFact.class).addMappings(mapper -> mapper.using((MappingContext<String, YaSeason> context) -> YaSeason.fromString(context.getSource())).map(YaFactDto::getSeason, YaFact::setSeason))
                .addMappings(mapper -> mapper.using((MappingContext<String, YaDaytime> context) -> YaDaytime.fromString(context.getSource())).map(YaFactDto::getDaytime, YaFact::setDaytime));

        modelMapper.createTypeMap(YaForecastDto.class, YaForecast.class).addMappings(mapper -> mapper.using(partListDtoToPartList).map(YaForecastDto::getParts, YaForecast::addParts));
        modelMapper.createTypeMap(YaForecast.class, YaForecastDto.class).addMappings(mapper -> mapper.using(partListDtoFromPartList).map(YaForecast::getParts, YaForecastDto::setParts));
    }

    public YaMessage messageDtoToMessage(final YaMessageDto dto) {
        return modelMapper.map(dto, YaMessage.class);
    }

    public YaFact factDtoToFact(final YaFactDto dto) {
        return modelMapper.map(dto, YaFact.class);
    }

    public YaForecast forecastDtoToForecast(final YaForecastDto dto) {
        return modelMapper.map(dto, YaForecast.class);
    }

    public List<YaPart> partListDtoToPartList(final List<YaPartDto> dtoList) {
        final Type listType = new TypeToken<List<YaPart>>() {
        }.getType();
        return modelMapper.map(dtoList, listType);
    }

    public YaMessageDto messageDtoFromMessage(final YaMessage entity) {
        return modelMapper.map(entity, YaMessageDto.class);
    }

    public YaFactDto factDtoFromFact(final YaFact entity) {
        return modelMapper.map(entity, YaFactDto.class);
    }

    public YaForecastDto forecastDtoFromForecast(final YaForecast entity) {
        return modelMapper.map(entity, YaForecastDto.class);
    }

    public List<YaPartDto> partListDtoFromPartList(final List<YaPart> entityList) {
        final Type listType = new TypeToken<List<YaPartDto>>() {
        }.getType();
        return modelMapper.map(entityList, listType);
    }

    public List<YaMessageDto> messagesDtoFromMessages(final List<YaMessage> entityList) {
        final Type listType = new TypeToken<List<YaMessageDto>>() {
        }.getType();
        return modelMapper.map(entityList, listType);
    }

    public List<YaFactDto> factsDtoFromFacts(final List<YaFact> entityList) {
        final Type listType = new TypeToken<List<YaFactDto>>() {
        }.getType();
        return modelMapper.map(entityList, listType);
    }

}
