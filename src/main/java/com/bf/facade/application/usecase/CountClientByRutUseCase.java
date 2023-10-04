package com.bf.facade.application.usecase;

import org.springframework.stereotype.Component;

import com.bf.facade.application.port.out.ClientJDBCRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CountClientByRutUseCase {
    private final ClientJDBCRepository clientJDBCRepository;

    public Integer countClientsByRut(String rut) {
        return clientJDBCRepository.countClientsByRut(rut);
    }
}