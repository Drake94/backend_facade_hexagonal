package com.bf.facade.adapter.rest.card;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bf.facade.adapter.rest.card.model.card.CardModel;
import com.bf.facade.adapter.rest.exception.BadRequestRestCardException;
import com.bf.facade.adapter.rest.exception.EmptyOrNullBodyRestCardException;
import com.bf.facade.adapter.rest.exception.NotFoundRestCardException;
import com.bf.facade.adapter.rest.exception.TimeoutRestCardException;
import com.bf.facade.adapter.rest.handler.RestTemplateErrorHandler;
import com.bf.facade.application.port.out.CardRepository;
import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.property.CardProperty;
import com.bf.facade.domain.Card;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CardRestClientAdapter implements CardRepository{
private final CardProperty property;
private final RestTemplate restTemplate;
    public CardRestClientAdapter(CardProperty property, RestTemplate restTemplate) {
        this.property = property;
        this.restTemplate = restTemplate;
        var errorHandler = new RestTemplateErrorHandler(
            Map.of(
                HttpStatus.NOT_FOUND, new NotFoundRestCardException(ErrorCode.CARD_NOT_FOUND),
                HttpStatus.REQUEST_TIMEOUT, new TimeoutRestCardException (ErrorCode.CARD_TIMEOUT),
                HttpStatus.BAD_REQUEST, new BadRequestRestCardException(ErrorCode.CARD_BAD_REQUEST)
            )
        );
        this.restTemplate.setErrorHandler(errorHandler);
    }

    @Override
    public Card getCard(String cardNumber) {
        log.info("Servicio obtener tarjeta, buscar [{}]", property.getUrl(property.getUrlCardNumber(), cardNumber));
        log.debug("Este mensaje no debe aparecer en el modo develop");
            CardModel response = Optional.ofNullable(restTemplate.getForObject(property.getUrl(property.getUrlCardNumber(), cardNumber), CardModel.class, cardNumber))
                .orElseThrow(()-> new EmptyOrNullBodyRestCardException(ErrorCode.CARD_NOT_FOUND));
            log.info("Respuesta obtenida desde el servicio obtener tarjeta data: [{}]", response);
            return response.toCardDomain();        
    }

    @Override
    public boolean existsCardByCardNumber(String cardNumber) {
        log.info("Verificando si existe un cliente con el numero de tarjeta: [{}]", cardNumber);

        try {
            var response = restTemplate.getForObject(property.getUrl(property.getUrlCardNumber(), cardNumber), CardModel.class, cardNumber);
            return response != null;

        } catch (RestClientException e) {
            log.error("Error al verificar la existencia de la tarjeta por numero de tarjeta: [{}]", cardNumber, e);
            return false;
        }
    }
}
