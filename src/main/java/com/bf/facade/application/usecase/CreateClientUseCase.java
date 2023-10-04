package com.bf.facade.application.usecase;

import org.springframework.stereotype.Component;

import com.bf.facade.adapter.rest.exception.DuplicatedClientException;
import com.bf.facade.application.port.in.CreateClient;
import com.bf.facade.application.port.out.ClientJDBCRepository;
import com.bf.facade.config.ErrorCode;
import com.bf.facade.domain.Client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class CreateClientUseCase implements CreateClient {
    private final ClientJDBCRepository clientJDBCRepository;

    @Override
    public Integer createClient(Client client) {
        Integer count = clientJDBCRepository.countClientsByRut(client.getRut());
        if (count > 0){
            log.error("Ya existe un cliente con el mismo rut [{}].", client.getRut());
            throw new DuplicatedClientException(ErrorCode.CLIENT_DUPLICATED);
        }
        return clientJDBCRepository.createClient(client);
    }
}
