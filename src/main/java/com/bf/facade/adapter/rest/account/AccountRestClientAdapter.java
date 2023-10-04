package com.bf.facade.adapter.rest.account;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bf.facade.adapter.rest.account.model.account.AccountModel;
import com.bf.facade.adapter.rest.exception.BadRequestRestAccountException;
import com.bf.facade.adapter.rest.exception.EmptyOrNullBodyRestAccountException;
import com.bf.facade.adapter.rest.exception.NotFoundRestAccountException;
import com.bf.facade.adapter.rest.exception.TimeoutRestAccountException;
import com.bf.facade.adapter.rest.handler.RestTemplateErrorHandler;
import com.bf.facade.application.port.out.AccountRepository;
import com.bf.facade.config.ErrorCode;
import com.bf.facade.config.property.AccountProperty;
import com.bf.facade.domain.Account;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class AccountRestClientAdapter implements AccountRepository {
private final AccountProperty property;
private final RestTemplate restTemplate;
    public AccountRestClientAdapter(AccountProperty property, RestTemplate restTemplate) {
        this.property = property;
        this.restTemplate = restTemplate;
        var errorHandler = new RestTemplateErrorHandler(
            Map.of(
                HttpStatus.NOT_FOUND, new NotFoundRestAccountException(ErrorCode.ACCOUNT_NOT_FOUND),
                HttpStatus.REQUEST_TIMEOUT, new TimeoutRestAccountException (ErrorCode.ACCOUNT_TIMEOUT),
                HttpStatus.BAD_REQUEST, new BadRequestRestAccountException(ErrorCode.ACCOUNT_BAD_REQUEST)
            )
        );
        this.restTemplate.setErrorHandler(errorHandler);
    }

    @Override
    public Account getAccount(Integer accountNumber) {
        String accountNumberStr = String.valueOf(accountNumber);
        log.info("Servicio obtener cliente, buscar [{}]", property.getUrl(property.getUrlAccountNumber(), accountNumberStr));
        log.debug("Este mensaje no debe aparecer en el modo develop");
            AccountModel response = Optional.ofNullable(restTemplate.getForObject(property.getUrl(property.getUrlAccountNumber(), accountNumberStr), AccountModel.class, accountNumberStr))
                .orElseThrow(()-> new EmptyOrNullBodyRestAccountException(ErrorCode.ACCOUNT_NOT_FOUND));
            log.info("Respuesta obtenida desde el servicio obtener cuenta data: [{}]", response);
            return response.toAccountDomain();
    }

    @Override
    public boolean existsAccountByAccountNumber(Integer accountNumber){
        log.info("Verficando si existe una cuenta con el numero de cuenta: [{}]", accountNumber);

        try {
            String accountNumberStr = String.valueOf(accountNumber);
            var response = restTemplate.getForObject(property.getUrl(property.getUrlAccountNumber(), accountNumberStr), AccountModel.class, accountNumberStr);
            return response != null;
        } catch (RestClientException e) {
            log.error("Error al verificar la existencia de la cuenta por numero de cuenta: [{}]", accountNumber, e);
            return false;
        }
    }
}
