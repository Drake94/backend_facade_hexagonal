package com.bf.facade.adapter.rest.client;

import com.bf.facade.config.property.ClientProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.client.MockRestServiceServer;

@DisplayName("ClientRestClientAdapterTest")
@Import(value = {ClientProperty.class})
@AutoConfigureWebClient(registerRestTemplate = true)
@RestClientTest(value={ClientRestClientAdapter.class})
class ClientRestClientAdapterTest {

    @Autowired
    private ClientRestClientAdapter adapter;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    MockRestServiceServer server;

    @Test
    @Name("When client rest is success call")
    void getExternalRestClientOk(){
        
    }

}