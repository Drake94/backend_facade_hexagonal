package com.bf.facade.adapter.rest.client;

import jdk.jfr.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.client.MockRestServiceServer;

import com.bf.facade.adapter.rest.card.CardRestClientAdapter;
import com.bf.facade.config.property.CardProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("CardRestClientAdapterTest")
@Import(value = {CardProperty.class})
@AutoConfigureWebClient(registerRestTemplate = true)
@RestClientTest(value={CardRestClientAdapter.class})
public class CardRestClientAdapterTest {
    @Autowired
    private CardRestClientAdapter adapter;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    MockRestServiceServer server;

    @Test
    @Name("When client rest is sucess call")
    void getExternalRestClientOk(){
        
    }
}
