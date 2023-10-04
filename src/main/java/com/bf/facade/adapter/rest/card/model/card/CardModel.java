package com.bf.facade.adapter.rest.card.model.card;

import com.bf.facade.domain.Card;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CardModel {

    private Integer id;
    private Integer account;
    private String cardNumber;
    private Integer type;
    private String descriptionType;
    private Integer status;
    private String descriptionStatus;

    public Card toCardDomain() {
        return Card.builder()
                .account(account)
                .cardNumber(cardNumber)
                .type(type)
                .descriptionType(descriptionType)
                .status(status)
                .descriptionStatus(descriptionStatus)
                .build();
    }
}