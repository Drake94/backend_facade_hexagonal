package com.bf.facade.adapter.rest.client.model.client;


import com.bf.facade.domain.Client;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClientModel {
    private Integer id;
    private String rut;
    private String name;
    private Integer nationality;
    private String birthDate;

    public Client toClientDomain(){
        return Client.builder()
            .rut(rut)
            .name(name)
            .nationality(nationality)
            .birthDate(birthDate)
            .build();
    }
}