package com.bf.facade.application.usecase;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Component;

import com.bf.facade.application.port.in.GetCard;
import com.bf.facade.application.port.out.CardJDBCRepository;
import com.bf.facade.application.port.out.CardRepository;
import com.bf.facade.domain.Card;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class GetCardUseCase implements GetCard{
    private final CardRepository cardRepository;

    private final CardJDBCRepository cardJDBCRepository;

    @Override
    public Card getCard(String cardNumber) throws ExecutionException, InterruptedException {
        Card card = cardRepository.getCard(cardNumber);

        log.info("Tarjeta obtenida [{}]", cardNumber);

        return Card.builder()
                .account(card.getAccount())
                .type(card.getType())
                .descriptionType(card.getDescriptionType())
                .status(card.getStatus())
                .descriptionStatus(card.getDescriptionStatus())
                .build();
    }

    @Override
    public Card getInternal(String cardNumber) {
        return cardJDBCRepository.getCardByCardNumber(cardNumber);
    }
}
