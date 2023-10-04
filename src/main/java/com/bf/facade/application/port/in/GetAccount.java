package com.bf.facade.application.port.in;

import java.util.concurrent.ExecutionException;

import com.bf.facade.domain.Account;

public interface GetAccount {
    Account getAccount(Integer accountNumber) throws ExecutionException, InterruptedException; 
    Account getInternal(Integer accountNumber);
}