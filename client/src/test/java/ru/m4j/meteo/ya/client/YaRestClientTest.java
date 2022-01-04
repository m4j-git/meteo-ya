/*
 * Copyright (c) 2002-2022 meteo@m4j.ru
 */
package ru.m4j.meteo.ya.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.m4j.meteo.ya.client.YaRestClientTest.RestClientTestConfig;
import ru.m4j.meteo.ya.model.YaFactDto;
import ru.m4j.meteo.ya.model.YaMessageDto;
import ru.m4j.meteo.ya.srv.config.YaTestBeanSource;

@RestClientTest(YaRestClientImpl.class)
@AutoConfigureWebClient(registerRestTemplate = true)
@ContextConfiguration(classes = { YaRestClientImpl.class, YaTestBeanSource.class })
@Import(RestClientTestConfig.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
class YaRestClientTest {

    private final Integer geonameId = 1;

    @Autowired
    private YaRestClientImpl client;
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private ObjectMapper jacksonMapper;
    @Autowired
    private YaTestBeanSource src;

    @Test
    void whenCallingGetFacts_thenClientMakesCorrectCall() throws IOException {
        YaMessageDto dto = src.readJson();
        List<YaFactDto> factList = new ArrayList<>();
        factList.add(dto.getFact());
        String json = jacksonMapper.writeValueAsString(factList);

        server.expect(requestTo(client.getUri("messages/facts", geonameId)))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        List<YaFactDto> wf = client.getFacts(geonameId, null, null);
        assertThat(wf.size()).isPositive();
    }

    @Test
    void whenCallingGetLastMessage_thenClientMakesCorrectCall() throws IOException {
        YaMessageDto dto = src.readJson();
        String json = jacksonMapper.writeValueAsString(dto);

        server.expect(requestTo(client.getUri("messages/last", geonameId)))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        YaMessageDto wf = client.getLastMessage(geonameId);
        assertThat(wf.getFact().getTemp()).isNotNull();
    }

    @Test
    void whenCallingGetMessages_thenClientMakesCorrectCall() throws IOException {
        YaMessageDto dto = src.readJson();
        YaMessageDto[] mesList = new YaMessageDto[] { dto };
        String json = jacksonMapper.writeValueAsString(mesList);
        server.expect(requestTo(client.getUri("messages", geonameId)))
            .andRespond(withSuccess(json, MediaType.APPLICATION_JSON));
        List<YaMessageDto> wf = client.getMessages(geonameId, null, null);
        assertThat(wf.size()).isPositive();
    }

    @TestConfiguration
    static class RestClientTestConfig {

        @Bean
        public YaRestClientImpl client(RestTemplate restTemplate) {
            return new YaRestClientImpl(restTemplate);
        }
    }

}
