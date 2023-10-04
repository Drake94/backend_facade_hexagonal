package com.bf.facade.adapter.jdbc.postgres;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.bf.facade.adapter.jdbc.dao.sql.GenericDAO;
import com.bf.facade.adapter.jdbc.dao.sql.SqlReader;
import com.bf.facade.adapter.jdbc.postgres.model.AccountJDBCModel;
import com.bf.facade.adapter.rest.exception.BadRequestRestAccountException;
import com.bf.facade.adapter.rest.exception.DuplicatedAccountException;
import com.bf.facade.application.port.out.AccountJDBCRepository;
import com.bf.facade.config.ErrorCode;
import com.bf.facade.domain.Account;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AccountJDBCAdapter implements AccountJDBCRepository {
    private static final String SQL_GET_ACCOUNT = "sql/get-account.sql";
    private static final String SQL_INSERT_ACCOUNT = "sql/insert-account.sql";
    private static final String SQL_EXISTS_ACCOUNT_BY_ACCOUNTNUMBER = "sql/exists-account-by-accountNumber.sql";
    private static final String KEY_CLIENT_ACCOUNTNUMBER = "accountNumber";
    private static final String KEY_CLIENT_BALANCE = "balance";
    private final GenericDAO dao;

    private final String getAccount;
    private final String insertAccount;
    private final String countAccountsByAccountNumber;

    public AccountJDBCAdapter(final GenericDAO dao) {
        this.dao = dao;
        this.getAccount = SqlReader.read(SQL_GET_ACCOUNT);
        this.insertAccount = SqlReader.read(SQL_INSERT_ACCOUNT);
        this.countAccountsByAccountNumber = SqlReader.read(SQL_EXISTS_ACCOUNT_BY_ACCOUNTNUMBER);
    }

    @Override
    public Integer createAccount(Account account) {
        log.info("Insertando una nueva cuenta en la BD [{}]", account);

        int count = countAccountByAccountNumber(account.getAccountNumber());

        if (count > 0){
            log.error("Ya existe una cuenta con el mismo numero de cuenta: [{}]", account.getAccountNumber());
            throw new DuplicatedAccountException(ErrorCode.ACCOUNT_DUPLICATED);
        }

        var params = new MapSqlParameterSource()
            .addValue(KEY_CLIENT_ACCOUNTNUMBER, account.getAccountNumber())
            .addValue(KEY_CLIENT_BALANCE, account.getBalance());
        return dao.insert(insertAccount, params, null).intValue();
    }

    @Override
    public Account getAccountByAccountNumber(Integer accountNumber) {
        var parameter = new MapSqlParameterSource().addValue(KEY_CLIENT_ACCOUNTNUMBER, accountNumber);
            log.info("Se va a realizar la busqueda de la cuenta cuyo numero de cuenta es: [{}]", accountNumber);
        var response = dao.findOne(getAccount, parameter, AccountJDBCModel.class)
            .orElseThrow(()-> new BadRequestRestAccountException(ErrorCode.ACCOUNT_NOT_FOUND));
        return response.toDomain();
    }

    @Override
    public Integer countAccountByAccountNumber (Integer accountNumber) {
        var parameter = new MapSqlParameterSource().addValue(KEY_CLIENT_ACCOUNTNUMBER, accountNumber);
            log.info("Se va a realizar la verificación de si existe una cuenta con el numero de cuenta: [{}]", accountNumber);
        
        try {
            var count = dao.queryForObject(countAccountsByAccountNumber, parameter, Integer.class);
            return count != null ? ((Integer) count).intValue() : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }
}