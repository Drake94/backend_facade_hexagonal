package com.bf.facade.application.usecase;

import org.springframework.stereotype.Component;

import com.bf.facade.application.port.out.AccountJDBCRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CountAccountByAccountNumberUseCase {
    private final AccountJDBCRepository accountJDBCRepository;

    public Integer countAccountsByRut(Integer accountNumber){
        return accountJDBCRepository.countAccountByAccountNumber(accountNumber);
    }
}
