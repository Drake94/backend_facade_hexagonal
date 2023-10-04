package com.bf.facade.application.port.out;

import com.bf.facade.domain.Account;

public interface AccountRepository {
    Account getAccount(Integer accountNumber);
    boolean existsAccountByAccountNumber(Integer accountNumber);
}
