package com.bf.facade.application.port.out;

import com.bf.facade.domain.Card;

public interface CardJDBCRepository {
    Integer createCard(Card card);
    Card getCardByCardNumber(String cardNumber);
    Integer countCardByCardNumber(String cardNumber);
}
