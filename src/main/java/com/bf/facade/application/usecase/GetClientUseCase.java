package com.bf.facade.application.usecase;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Component;

import com.bf.facade.application.port.in.GetClient;
import com.bf.facade.application.port.out.ClientJDBCRepository;
import com.bf.facade.application.port.out.ClientRepository;
import com.bf.facade.domain.Client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class GetClientUseCase implements GetClient {
    private final ClientRepository clientRepository;
    
    private final ClientJDBCRepository clientJDBCRepository;

    @Override
    public Client getClient(String rut) throws ExecutionException, InterruptedException {
        Client client = clientJDBCRepository.getClient(rut);

        log.info("Cliente obtenido [{}]", rut);

        return Client.builder()
                .rut(client.getRut())
                .name(client.getName())
                .nationality(client.getNationality())
                .birthDate(client.getBirthDate())
                .build();
    }

    @Override
    public Client getInternal(String rut) {
        return clientJDBCRepository.getClientById(rut);
    }
}
