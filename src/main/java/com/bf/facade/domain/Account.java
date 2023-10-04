package com.bf.facade.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
@With
public class Account {
    Integer accountNumber;
    Integer balance;
}
