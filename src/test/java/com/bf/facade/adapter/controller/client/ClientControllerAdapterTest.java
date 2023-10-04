package com.bf.facade.adapter.controller.client;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bf.facade.adapter.rest.exception.BadRequestRestClientException;
import com.bf.facade.adapter.rest.exception.EmptyOrNullBodyRestClientException;
import com.bf.facade.application.port.in.CreateClient;
import com.bf.facade.application.port.in.GetClient;
import com.bf.facade.config.ErrorCode;
import com.bf.facade.mocks.ClientMock;
import com.bf.facade.utils.UtilsByTest;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DisplayName("ClientControllerAdapterTest")
@WebMvcTest(ClientControllerAdapter.class)
@ExtendWith(MockitoExtension.class)
class ClientControllerAdapterTest {
    private static final String GET_CLIENT = "/api/v1.0/accounts/client/{rut}";
    private static final String GET_INTERNAL_CLIENT= "/api/v1.0/accounts/client/internal/{rut}";

    @Autowired
    private MockMvc restRequest;

    @MockBean
    private GetClient getClient;

    @MockBean
    private CreateClient createClient;

    @Test
    @DisplayName("When Get client is success")
    void getClientSuccessExternal() throws Exception {
        when(getClient.getClient(ClientMock.STRING_VALUE)).thenReturn(ClientMock.getClientDomain());
        restRequest.perform(MockMvcRequestBuilders.get(GET_CLIENT, ClientMock.STRING_VALUE))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("When Get client is fail bad request")
    void getClientBadRequestExternal() throws Exception {
        when(getClient.getClient(anyString())).thenThrow(new BadRequestRestClientException(ErrorCode.CLIENT_NOT_FOUND));
        restRequest.perform(MockMvcRequestBuilders.get(GET_CLIENT, ClientMock.STRING_VALUE))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("When Get client is fail EmptyOrNullBodyRestClientException")
    void getClientEmptyOrNullBodyExternal() throws Exception {
        when(getClient.getClient(anyString())).thenThrow(new EmptyOrNullBodyRestClientException(ErrorCode.CLIENT_NOT_FOUND));
        restRequest.perform(MockMvcRequestBuilders.get(GET_CLIENT, ClientMock.STRING_VALUE))
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("When Get client internal is success")
    void getClientSuccessInternal() throws Exception {
        when(getClient.getInternal(ClientMock.STRING_VALUE)).thenReturn(ClientMock.getClientDomain());
        restRequest.perform(MockMvcRequestBuilders.get(GET_INTERNAL_CLIENT, ClientMock.STRING_VALUE))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("When client is create success")
    void createClientSucessInternal() throws Exception {
        when(createClient.createClient(ClientMock.getClientDomain())).thenReturn(0);
        restRequest.perform(MockMvcRequestBuilders.post(String.format(ClientMock.CREATE_CLIENT))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UtilsByTest.JsonToString(ClientMock.getClientRest())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(UtilsByTest.JsonToString(ClientMock.getRestResponseCreateClient())));
    }
}