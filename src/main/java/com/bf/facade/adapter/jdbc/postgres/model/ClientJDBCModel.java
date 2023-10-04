package com.bf.facade.adapter.jdbc.postgres.model;

import java.io.Serializable;

import com.bf.facade.domain.Client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientJDBCModel implements Serializable {
    private String rut;
    private String name;
    private Integer nationality;
    private String birthDate;

    public Client toDomain() {
        return Client.builder()
            .rut(this.rut)
            .name(this.name)
            .nationality(this.nationality)
            .birthDate(this.birthDate)
            .build();
    }

}