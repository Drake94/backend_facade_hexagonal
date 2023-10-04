package com.bf.facade.adapter.controller.model.card;

import java.util.Objects;

import com.bf.facade.domain.Card;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class CardRest {

    Integer account;
    String cardNumber;
    Integer type;
    String descriptionType;
    Integer status;
    String descriptionStatus;

    @JsonCreator
    public CardRest(@JsonProperty("account") Integer account, @JsonProperty("cardNumber") String cardNumber, @JsonProperty("type") Integer type, @JsonProperty("descriptionType") String descriptionType, @JsonProperty("status") Integer status, @JsonProperty("descriptionStatus") String descriptionStatus) {
        this.account = account;
        this.cardNumber = cardNumber;
        this.type = type;
        this.descriptionType = descriptionType;
        this.status = status;
        this.descriptionStatus = descriptionStatus;
    }

    public static CardRest toCardRest(Card card) {
        return Objects.nonNull(card) ?
            CardRest.builder()
                    .account(card.getAccount())
                    .cardNumber(card.getCardNumber())
                    .type(card.getType())
                    .descriptionType(card.getDescriptionType())
                    .status(card.getStatus())
                    .descriptionStatus(card.getDescriptionStatus())
                    .build() : null;
    }

    public Card toDomain() {
        return Card.builder()
                .account(this.account)
                .cardNumber(this.cardNumber)
                .type(this.type)
                .descriptionType(this.descriptionType)
                .status(this.status)
                .descriptionStatus(this.descriptionStatus)
                .build();
    }
}

