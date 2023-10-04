package com.bf.facade.adapter.controller.model.client;

import java.util.Objects;

import com.bf.facade.domain.Client;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClientRest {
    String rut;
    String name;
    Integer nationality;
    String birthDate;
    
    @JsonCreator
    public ClientRest(@JsonProperty("rut") String rut, @JsonProperty("name") String name, @JsonProperty("nationality") Integer nationality, @JsonProperty("birthDate") String birthDate) {
        this.rut = rut;
        this.name = name;
        this.nationality = nationality;
        this.birthDate = birthDate;
    }

    public static ClientRest toClientRest(Client client){
        return Objects.nonNull(client) ?
            ClientRest.builder()
                .rut(client.getRut())
                .name(client.getName())
                .nationality(client.getNationality())
                .birthDate(client.getBirthDate())
                .build() : null;
    }
    public Client toDomain(){
        return Client.builder()
            .rut(this.rut)
            .name(this.name)
            .nationality(this.nationality)
            .birthDate(this.birthDate)
            .build();
    }
}