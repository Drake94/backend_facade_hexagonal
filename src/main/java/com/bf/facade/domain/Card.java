package com.bf.facade.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
@With
public class Card {
    Integer account;
    String cardNumber;
    Integer type;
    String descriptionType;
    Integer status;
    String descriptionStatus;
}
