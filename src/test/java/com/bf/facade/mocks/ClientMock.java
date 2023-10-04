package com.bf.facade.mocks;

import org.springframework.http.HttpStatus;

import com.bf.facade.adapter.controller.model.RestResponse;
import com.bf.facade.adapter.controller.model.client.ClientRest;
import com.bf.facade.domain.Client;

import java.util.Collections;

public final class ClientMock {
    public static final String STRING_VALUE = "10123436-1";
    public static final String STRING_VALUE2 = "Jhon Doe";
    public static final Integer INTEGER_VALUE1 = 1;
    public static final String STRING_VALUE4 = "1999-01-05";
    public static final String CREATE_CLIENT = "/api/v1.0/accounts/client/create";

    public static ClientRest getClientRest() {
        return ClientRest.builder()
                .rut(STRING_VALUE)
                .name(STRING_VALUE2)
                .nationality(INTEGER_VALUE1)
                .birthDate(STRING_VALUE4)
                .build();
    }
    public static Client getClientDomain() {
        return Client.builder()
                .rut(STRING_VALUE)
                .name(STRING_VALUE2)
                .nationality(INTEGER_VALUE1)
                .birthDate(STRING_VALUE4)
                .build();
    }
    public static RestResponse<Integer> getRestResponseCreateClient(){
        return new RestResponse<>(
                "",
                HttpStatus.CREATED.value(),
                CREATE_CLIENT,
                0,
                Collections.emptyMap());
    }
}