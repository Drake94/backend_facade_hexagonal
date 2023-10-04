package com.bf.facade.application.usecase;

import org.springframework.stereotype.Component;

import com.bf.facade.application.port.out.CardJDBCRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CountCardByCardNumberUseCase {
    private final CardJDBCRepository cardJDBCRepository;

    public Integer countCardsByCardNumber(String cardNumber){
        return cardJDBCRepository.countCardByCardNumber(cardNumber);
    }
}