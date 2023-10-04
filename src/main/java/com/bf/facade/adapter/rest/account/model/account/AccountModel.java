package com.bf.facade.adapter.rest.account.model.account;

import com.bf.facade.domain.Account;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountModel {
    private Integer id;
    private Integer accountNumber;
    private Integer balance;

    public Account toAccountDomain() {
        return Account.builder()
            .accountNumber(accountNumber)
            .balance(balance)
            .build();
    }
}