package com.bf.facade.adapter.controller.account;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bf.facade.adapter.rest.exception.BadRequestRestAccountException;
import com.bf.facade.adapter.rest.exception.EmptyOrNullBodyRestClientException;
import com.bf.facade.application.port.in.CreateAccount;
import com.bf.facade.application.port.in.GetAccount;
import com.bf.facade.config.ErrorCode;
import com.bf.facade.mocks.AccountMock;
import com.bf.facade.mocks.ClientMock;
import com.bf.facade.utils.UtilsByTest;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@DisplayName("AccountControllerAdapterTest")
@WebMvcTest(AccountControllerAdapter.class)
@ExtendWith(MockitoExtension.class)
class AccountControllerAdapterTest {
    private static final String GET_ACCOUNT = "/api/v1.0/account/{accountNumber}";
    private static final String GET_INTERNAL_ACCOUNT= "/api/v1.0/account/internal/{accountNumber}";

    @Autowired
    private MockMvc restRequest;

    @MockBean
    private GetAccount getAccount;

    @MockBean
    private CreateAccount createAccount;

    @Test
    @DisplayName("When Get account is success")
    void getAccountSuccesExternal() throws Exception {
        when(getAccount.getAccount(AccountMock.INTEGER_VALUE1)).thenReturn(AccountMock.getAccountDomain());
        restRequest.perform(MockMvcRequestBuilders.get(GET_ACCOUNT, AccountMock.INTEGER_VALUE1))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("When Get account is fail bad request")
    void getAccountBadRequestExternal() throws Exception {
        when(getAccount.getAccount(anyInt())).thenThrow(new BadRequestRestAccountException(ErrorCode.ACCOUNT_NOT_FOUND));
        restRequest.perform(MockMvcRequestBuilders.get(GET_ACCOUNT, AccountMock.INTEGER_VALUE1))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("When Get account is fail EmptyOrNullBodyRestClientException")
    void getAccountEmptyOrNullBodyExternal() throws Exception {
        when(getAccount.getAccount(anyInt())).thenThrow(new EmptyOrNullBodyRestClientException(ErrorCode.ACCOUNT_NOT_FOUND));
        restRequest.perform(MockMvcRequestBuilders.get(GET_ACCOUNT, ClientMock.INTEGER_VALUE1))
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("When Get account internal is success")
    void getAccountSuccessInternal() throws Exception {
        when(getAccount.getInternal(AccountMock.INTEGER_VALUE1)).thenReturn(AccountMock.getAccountDomain());
        restRequest.perform(MockMvcRequestBuilders.get(GET_INTERNAL_ACCOUNT, ClientMock.INTEGER_VALUE1))
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("When account is create success")
    void createAccountSucessInternal() throws Exception {
        when(createAccount.createAccount(AccountMock.getAccountDomain())).thenReturn(0);
        restRequest.perform(MockMvcRequestBuilders.post(String.format(AccountMock.CREATE_ACCOUNT))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(UtilsByTest.JsonToString(AccountMock.getAccountRest())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(UtilsByTest.JsonToString(AccountMock.getRestResponseCreateAccount())));
    }
}