package com.bf.facade.adapter.rest.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import jdk.jfr.Name;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.client.MockRestServiceServer;

import com.bf.facade.adapter.rest.account.AccountRestClientAdapter;
import com.bf.facade.config.property.AccountProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("AccountRestClientAdapterTest")
@Import(value = {AccountProperty.class})
@AutoConfigureWebClient(registerRestTemplate = true)
@RestClientTest(value={AccountRestClientAdapter.class})
class AccountRestClientAdapterTest {
    
    @Autowired
    private AccountRestClientAdapter adapter;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    MockRestServiceServer server;

    @Test
    @Name("When client rest is sucess call")
    void getExternalRestClientOk(){
        
    }
}
