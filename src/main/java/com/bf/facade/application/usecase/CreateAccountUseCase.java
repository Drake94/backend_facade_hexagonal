package com.bf.facade.application.usecase;

import org.springframework.stereotype.Component;

import com.bf.facade.adapter.rest.exception.DuplicatedAccountException;
import com.bf.facade.application.port.in.CreateAccount;
import com.bf.facade.application.port.out.AccountJDBCRepository;
import com.bf.facade.config.ErrorCode;
import com.bf.facade.domain.Account;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class CreateAccountUseCase implements CreateAccount {
    private final AccountJDBCRepository accountJDBCRepository;

    @Override
    public Integer createAccount(Account account) {
        Integer count = accountJDBCRepository.countAccountByAccountNumber(account.getAccountNumber());
        if (count > 0){
            log.error("Ya existe una cuenta con el mismo numero de cuenta: [{}]", account.getAccountNumber());
            throw new DuplicatedAccountException(ErrorCode.ACCOUNT_DUPLICATED);
        }
        return accountJDBCRepository.createAccount(account);
    }
}