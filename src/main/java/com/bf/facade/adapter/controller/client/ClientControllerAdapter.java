package com.bf.facade.adapter.controller.client;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;

import com.bf.facade.adapter.controller.model.RestResponse;
import com.bf.facade.adapter.controller.model.client.ClientRest;
import com.bf.facade.adapter.controller.processor.Processor;
import com.bf.facade.adapter.controller.processor.RequestProcessor;
import com.bf.facade.application.port.in.CreateClient;
import com.bf.facade.application.port.in.GetClient;
import com.bf.facade.domain.Client;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1.0/")
public final class ClientControllerAdapter {
    private static final String GET_CLIENT = "accounts/client/{rut}";
    private static final String GET_INTERNAL_CLIENT = "accounts/client/internal/{rut}";
    private static final String CREATE_CLIENT = "accounts/client/create";
    private final GetClient getClient;
    private final CreateClient createClient;
    private final Processor processor;

    public ClientControllerAdapter(GetClient getClient, CreateClient createClient) {
        this.getClient = getClient;
        this.createClient = createClient;
        this.processor = new RequestProcessor();
    }

    @GetMapping(GET_CLIENT)
    public RestResponse<ClientRest> getClient(final HttpServletRequest httpServletRequest, @PathVariable("rut") String rut) throws ExecutionException, InterruptedException {
        log.info("Llamada al servicio cliente/{}", rut);
        Client client = this.getClient.getClient(rut);
        ClientRest response = ClientRest.toClientRest(client);
        log.info("Respuesta del servicio cliente/{}: [{}]", rut, response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
            resp -> RestResponse.<ClientRest>builder()
                .data(response)
                .id(resp.getId())
                .status(HttpStatus.OK.value())
                .resource(httpServletRequest.getRequestURI())
                .metadata(processor.buildMetadata(resp.getReq()))
                .build()
        );
    }

    @GetMapping(GET_INTERNAL_CLIENT)
    public RestResponse<ClientRest> getInternalClient(final HttpServletRequest httpServletRequest,
        @PathVariable("rut") String rut) {
        log.info("Llamada al servicio clientes/internal/{}", rut);
        Client client = this.getClient.getInternal(rut);
        ClientRest response = ClientRest.toClientRest(client);
        log.info("Respuesta del servicio clientes/internal/{} : [{}]", rut, response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
            resp -> RestResponse.<ClientRest>builder()
                .data(response)
                .id(resp.getId())
                .status(HttpStatus.OK.value())
                .resource(httpServletRequest.getRequestURI())
                .metadata(processor.buildMetadata(resp.getReq()))
                .build()
        );
    }

    @PostMapping(CREATE_CLIENT)
    public RestResponse<Integer> createClient(
            final HttpServletRequest httpServletRequest,
            @Valid @RequestBody ClientRest request
    ){
        log.info("Llamada al servicio creación de un cliente");
        var response = createClient.createClient(request.toDomain());
        log.info("Respuesta del servicio cliente : [{}]", response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
            resp -> RestResponse.<Integer>builder()
                .data(response)
                .id(resp.getId())
                .status(HttpStatus.CREATED.value())
                .resource(httpServletRequest.getRequestURI())
                .metadata(processor.buildMetadata(resp.getReq()))
                .build()
        );
    }

}
