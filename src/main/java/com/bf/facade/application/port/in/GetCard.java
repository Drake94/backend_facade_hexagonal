package com.bf.facade.application.port.in;

import java.util.concurrent.ExecutionException;

import com.bf.facade.domain.Card;

public interface GetCard {
    Card getCard(String cardNumber) throws ExecutionException, InterruptedException;
    Card getInternal(String cardNmber);
}