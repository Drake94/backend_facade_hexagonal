package com.bf.facade.mocks;

import java.util.Collections;

import org.springframework.http.HttpStatus;

import com.bf.facade.adapter.controller.model.RestResponse;
import com.bf.facade.adapter.controller.model.card.CardRest;
import com.bf.facade.domain.Card;

public class CardMock {
    public static final String STRING_VALUE1 = "ES12321312";
    public static final Integer INTEGER_VALUE1 = 0;
    public static final String STRING_VALUE2 = "Titular";
    public static final Integer INTEGER_VALUE2 = 1;
    public static final String STRING_VALUE3 = "Activa";
    public static final String CREATE_CARD = "/api/v1.0/card/create";

    public static CardRest getCardRest() {
        return CardRest.builder()
            .cardNumber(STRING_VALUE1)
            .type(INTEGER_VALUE1)
            .descriptionType(STRING_VALUE2)
            .status(INTEGER_VALUE2)
            .descriptionStatus(STRING_VALUE3)
            .build();
    }
    public static Card getCardDomain() {
        return Card.builder()
            .cardNumber(STRING_VALUE1)
            .type(INTEGER_VALUE1)
            .descriptionType(STRING_VALUE2)
            .status(INTEGER_VALUE2)
            .descriptionStatus(STRING_VALUE3)
            .build();
    }
    public static RestResponse<Integer> getRestResponseCreateCard(){
        return new RestResponse<>(
                "",
                HttpStatus.CREATED.value(),
                CREATE_CARD,
                0,
                Collections.emptyMap());
    }
}
