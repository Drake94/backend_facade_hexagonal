package com.bf.facade.adapter.controller.model.account;

import java.util.Objects;

import com.bf.facade.domain.Account;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountRest {
    Integer accountNumber;
    Integer balance;

    @JsonCreator
    public AccountRest(@JsonProperty("accountNumber") Integer accountNumber, @JsonProperty("balance") Integer balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public static AccountRest toAccountRest(Account account){
        return Objects.nonNull(account) ?
            AccountRest.builder()
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .build() : null;
    }

    public Account toDomain(){
        return Account.builder()
            .accountNumber(this.accountNumber)
            .balance(this.balance)
            .build();
    }
}
