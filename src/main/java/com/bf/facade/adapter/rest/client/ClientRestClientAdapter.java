package com.bf.facade.adapter.rest.client;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bf.facade.adapter.rest.client.model.client.ClientModel;
import com.bf.facade.adapter.rest.exception.BadRequestRestClientException;
import com.bf.facade.adapter.rest.exception.EmptyOrNullBodyRestClientException;
import com.bf.facade.adapter.rest.exception.NotFoundRestClientException;
import com.bf.facade.adapter.rest.exception.TimeoutRestClientException;
import com.bf.facade.adapter.rest.handler.RestTemplateErrorHandler;
import com.bf.facade.application.port.out.ClientRepository;
import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.property.ClientProperty;
import com.bf.facade.domain.Client;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ClientRestClientAdapter implements ClientRepository {
private final ClientProperty property;
private final RestTemplate restTemplate;
    public ClientRestClientAdapter(ClientProperty property, RestTemplate restTemplate) {
        this.property = property;
        this.restTemplate = restTemplate;
        var errorHandler = new RestTemplateErrorHandler(
            Map.of(
                HttpStatus.NOT_FOUND, new NotFoundRestClientException(ErrorCode.CLIENT_NOT_FOUND),
                HttpStatus.REQUEST_TIMEOUT, new TimeoutRestClientException (ErrorCode.CLIENT_TIMEOUT),
                HttpStatus.BAD_REQUEST, new BadRequestRestClientException(ErrorCode.CLIENT_BAD_REQUEST)
            )
        );
        this.restTemplate.setErrorHandler(errorHandler);
       
    }

    @Override
    public Client getClient(String rut) {
        log.info("Servicio obtener cuenta, buscar [{}]",property.getUrl(property.getUrlRut(), rut));
        log.debug("Este mensaje no debe aparecer en el modo develop");
            ClientModel response = Optional.ofNullable(restTemplate.getForObject(property.getUrl(property.getUrlRut(), rut), ClientModel.class, rut))
                .orElseThrow(()-> new EmptyOrNullBodyRestClientException(ErrorCode.CLIENT_NOT_FOUND));
            log.info("Respuesta obtenida desde el servicio obtener cliente data: [{}]", response);
            return response.toClientDomain();
    }

    @Override
    public boolean existsClientByRut(String rut) {
        log.info("Verificando si existe un cliente con el rut: [{}]", rut);

        try {
            var response = restTemplate.getForObject(property.getUrl(property.getUrlRut(), rut), ClientModel.class, rut);
            return response != null;

        } catch (RestClientException e) {
            log.error("Error al verificar la existencia del cliente por rut: [{}]", rut, e);
            return false; 
        }
    }
}