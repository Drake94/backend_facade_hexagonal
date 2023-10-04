package com.bf.facade.mocks;

import java.util.Collections;

import org.springframework.http.HttpStatus;

import com.bf.facade.adapter.controller.model.RestResponse;
import com.bf.facade.adapter.controller.model.account.AccountRest;
import com.bf.facade.domain.Account;

public final class AccountMock {
    public static final Integer INTEGER_VALUE1 = 12;
    public static final Integer INTEGER_VALUE2 = 5000000;
    public static final String CREATE_ACCOUNT = "/api/v1.0/account/create";
    
    public static AccountRest getAccountRest() {
        return AccountRest.builder()
            .accountNumber(INTEGER_VALUE1)
            .balance(INTEGER_VALUE2)
            .build();
    }
    public static Account getAccountDomain() {
        return Account.builder()
            .accountNumber(INTEGER_VALUE1)
            .balance(INTEGER_VALUE2)
            .build();
    }
    public static RestResponse<Integer> getRestResponseCreateAccount(){
        return new RestResponse<>(
            "",
            HttpStatus.CREATED.value(),
            CREATE_ACCOUNT,
            0,
            Collections.emptyMap());
    }
}
