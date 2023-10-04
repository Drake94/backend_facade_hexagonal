package com.bf.facade.application.port.in;

import com.bf.facade.domain.Account;

public interface CreateAccount {
    Integer createAccount(Account account);
}