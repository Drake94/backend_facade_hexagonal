package com.bf.facade.application.usecase;

import org.springframework.stereotype.Component;

import com.bf.facade.adapter.rest.exception.DuplicatedCardException;
import com.bf.facade.application.port.in.CreateCard;
import com.bf.facade.application.port.out.CardJDBCRepository;
import com.bf.facade.config.ErrorCode;
import com.bf.facade.domain.Card;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class CreateCardUseCase implements CreateCard {
    private final CardJDBCRepository cardJDBCRepository;

    @Override
    public Integer createCard(Card card) {
        Integer count = cardJDBCRepository.countCardByCardNumber(card.getCardNumber());
        if (count > 0){
            log.error("Ya existe una tarjeta con el mismo numero de tarjeta [{}]", card.getCardNumber());
            throw new DuplicatedCardException(ErrorCode.CARD_DUPLICATED);
        }
        return cardJDBCRepository.createCard(card);
    }
}