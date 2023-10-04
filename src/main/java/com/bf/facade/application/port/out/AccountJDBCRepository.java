package com.bf.facade.application.port.out;

import com.bf.facade.domain.Account;

public interface AccountJDBCRepository {
    Integer createAccount(Account account);
    Account getAccountByAccountNumber(Integer accountNumber);
    Integer countAccountByAccountNumber(Integer accountNumber);
}