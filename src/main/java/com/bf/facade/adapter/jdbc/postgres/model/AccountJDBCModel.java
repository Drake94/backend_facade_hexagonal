package com.bf.facade.adapter.jdbc.postgres.model;

import java.io.Serializable;

import com.bf.facade.domain.Account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountJDBCModel implements Serializable{
    private Integer accountNumber;
    private Integer balance;

    public Account toDomain(){
        return Account.builder()
            .accountNumber(this.accountNumber)
            .balance(this.balance)
            .build();
    }
}