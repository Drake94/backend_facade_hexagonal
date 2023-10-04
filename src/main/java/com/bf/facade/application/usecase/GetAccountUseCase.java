package com.bf.facade.application.usecase;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Component;

import com.bf.facade.application.port.in.GetAccount;
import com.bf.facade.application.port.out.AccountJDBCRepository;
import com.bf.facade.application.port.out.AccountRepository;
import com.bf.facade.domain.Account;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class GetAccountUseCase implements GetAccount {
    private final AccountRepository accountRepository;

    private final AccountJDBCRepository accountJDBCRepository;

    @Override
    public Account getAccount(Integer accountNumber) throws ExecutionException, InterruptedException {
        Account account = accountRepository.getAccount(accountNumber);

        log.info("Cuenta obtenida [{}]", accountNumber);

        return Account.builder()
            .balance(account.getBalance())
            .build();
    }

    @Override
    public Account getInternal(Integer accountNumber) {
        return accountJDBCRepository.getAccountByAccountNumber(accountNumber);
    }
}