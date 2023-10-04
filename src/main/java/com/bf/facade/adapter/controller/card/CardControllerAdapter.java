package com.bf.facade.adapter.controller.card;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bf.facade.adapter.controller.model.RestResponse;
import com.bf.facade.adapter.controller.model.card.CardRest;
import com.bf.facade.adapter.controller.processor.Processor;
import com.bf.facade.adapter.controller.processor.RequestProcessor;
import com.bf.facade.application.port.in.CreateCard;
import com.bf.facade.application.port.in.GetCard;
import com.bf.facade.domain.Card;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1.0")
public class CardControllerAdapter {
    private static final String GET_CARD = "card/{cardNumber}";
    private static final String GET_INTERNAL_CARD = "card/internal/{cardNumber}";
    private static final String CREATE_CARD = "card/create";
    private final GetCard getCard;
    private final CreateCard createCard;
    private final Processor processor;

    public CardControllerAdapter(GetCard getCard, CreateCard createCard) {
        this.getCard = getCard;
        this.createCard = createCard;
        this.processor = new RequestProcessor();
    }

    @GetMapping(GET_CARD)
    public RestResponse<CardRest> getCard (final HttpServletRequest httpServletRequest, @PathVariable ("cardNumber") String cardNumber) throws ExecutionException, InterruptedException {
        log.info("Llamada al servicio tarjeta/{}", cardNumber);
        Card card = this.getCard.getCard(cardNumber);
        CardRest response = CardRest.toCardRest(card);
        log.info("Respuesta del servicio tarejta/{}: [{}]", cardNumber, response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
            resp -> RestResponse.<CardRest>builder()
                .data(response)
                .id(resp.getId())
                .status(HttpStatus.OK.value())
                .resource(httpServletRequest.getRequestURI())
                .metadata(processor.buildMetadata(resp.getReq()))
                .build()
        );
    }

    @GetMapping(GET_INTERNAL_CARD)
    public RestResponse<CardRest> getInternalCard(final HttpServletRequest httpServletRequest,
        @PathVariable("cardNumber") String cardNumber) {
        log.info("Llamada al servicio tarjetas/internal/{}", cardNumber);
        Card card = this.getCard.getInternal(cardNumber);
        CardRest response = CardRest.toCardRest(card);
        log.info("Respuesta del servicio tarjetas/internal/{} : [{}]", cardNumber, response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
            resp -> RestResponse.<CardRest>builder()
                .data(response)
                .id(resp.getId())
                .status(HttpStatus.OK.value())
                .resource(httpServletRequest.getRequestURI())
                .metadata(processor.buildMetadata(resp.getReq()))
                .build()
        );
    }

    @PostMapping(CREATE_CARD)
    public RestResponse<Integer> createCard(
            final HttpServletRequest httpServletRequest,
            @Valid @RequestBody CardRest request
    ){
        log.info("Llamada al servicio creación de una tarjeta");
        var response = createCard.createCard(request.toDomain());
        log.info("Respuesta del servicio tarjeta : [{}]", response);
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
