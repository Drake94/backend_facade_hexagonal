package com.bf.facade.application.port.out;

import com.bf.facade.domain.Card;

public interface CardRepository {
    Card getCard(String cardNumber);
    boolean existsCardByCardNumber(String cardNumber);
}