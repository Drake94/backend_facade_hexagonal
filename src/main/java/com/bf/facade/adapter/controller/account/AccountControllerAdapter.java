package com.bf.facade.adapter.controller.account;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bf.facade.adapter.controller.model.RestResponse;
import com.bf.facade.adapter.controller.model.account.AccountRest;
import com.bf.facade.adapter.controller.processor.Processor;
import com.bf.facade.adapter.controller.processor.RequestProcessor;
import com.bf.facade.application.port.in.CreateAccount;
import com.bf.facade.application.port.in.GetAccount;
import com.bf.facade.domain.Account;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1.0/")
public class AccountControllerAdapter {
    private static final String GET_ACCOUNT= "account/{accountNumber}";
    private static final String GET_INTERNAL_ACCOUNT = "/account/internal/{accountNumber}";
    private static final String CREATE_ACCOUNT = "account/create";
    private final GetAccount getAccount;
    private final CreateAccount createAccount;
    private final Processor processor;

    public AccountControllerAdapter(GetAccount getAccount, CreateAccount createAccount) {
        this.getAccount = getAccount;
        this.createAccount = createAccount;
        this.processor = new RequestProcessor();
    }

    @GetMapping(GET_ACCOUNT)
    public RestResponse<AccountRest> getAccount(final HttpServletRequest httpServletRequest, @PathVariable("accountNumber") Integer accountNumber) throws ExecutionException, InterruptedException {
        log.info("Llamada al servicio cuenta/{}", accountNumber);
        Account account = this.getAccount.getAccount(accountNumber);
        AccountRest response = AccountRest.toAccountRest(account);
        log.info("Respuesta del servicio cuenta/{}: [{}]", accountNumber, response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
            resp -> RestResponse.<AccountRest>builder()
                .data(response)
                .id(resp.getId())
                .status(HttpStatus.OK.value())
                .resource(httpServletRequest.getRequestURI())
                .metadata(processor.buildMetadata(resp.getReq()))
                .build()
        );
    }

    @GetMapping(GET_INTERNAL_ACCOUNT)
    public RestResponse<AccountRest> getInteralAccount(final HttpServletRequest httpServletRequest,
        @PathVariable("accountNumber") Integer accountNumber) {
        log.info("Llamada al servicio cuentas/internal/{}", accountNumber);
        Account account = this.getAccount.getInternal(accountNumber);
        AccountRest response = AccountRest.toAccountRest(account);
        log.info("Respuesta del servicio cuentas/internal/{} : [{}]", accountNumber, response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
            resp -> RestResponse.<AccountRest>builder()
                .data(response)
                .id(resp.getId())
                .status(HttpStatus.OK.value())
                .resource(httpServletRequest.getRequestURI())
                .metadata(processor.buildMetadata(resp.getReq()))
                .build()
        );
    }

    @PostMapping(CREATE_ACCOUNT)
    public RestResponse<Integer> createAccount(
            final HttpServletRequest httpServletRequest,
            @Valid @RequestBody AccountRest request
    ){
        log.info("Llamada al servicio creación de cuenta");
        var response = createAccount.createAccount(request.toDomain());
        log.info("Respuesta del servicio cuenta : [{}]", response);
        return processor.processRequest(Processor.Enriched.of(httpServletRequest),
            resp -> RestResponse.<Integer>builder()
                .data(response)
                .id(resp.getId())
                .status(HttpStatus.CREATED.value())
                .resource(httpServletRequest.getRequestURI())
                .metadata(processor.buildMetadata(resp.getReq()))
                .build()
        );
    }

}
