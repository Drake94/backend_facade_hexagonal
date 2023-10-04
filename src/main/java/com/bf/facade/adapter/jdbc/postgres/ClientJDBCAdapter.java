package com.bf.facade.adapter.jdbc.postgres;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.bf.facade.adapter.jdbc.dao.sql.GenericDAO;
import com.bf.facade.adapter.jdbc.dao.sql.SqlReader;
import com.bf.facade.adapter.jdbc.postgres.model.ClientJDBCModel;
import com.bf.facade.adapter.rest.exception.BadRequestRestClientException;
import com.bf.facade.adapter.rest.exception.DuplicatedClientException;
import com.bf.facade.application.port.out.ClientJDBCRepository;
import com.bf.facade.config.ErrorCode;
import com.bf.facade.domain.Client;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientJDBCAdapter implements ClientJDBCRepository {
    private static final String SQL_GET_CLIENT = "sql/get-client.sql";
    private static final String SQL_INSERT_CLIENT = "sql/insert-client.sql";
    private static final String SQL_EXISTS_CLIENT_BY_RUT = "sql/exists-client-by-rut.sql";
    private static final String KEY_CLIENT_RUT = "rut";
    private static final String KEY_CLIENT_NAME = "name";
    private static final String KEY_CLIENT_NATIONALITY = "nationality";
    private static final String KEY_CLIENT_BIRTHDATE = "birthDate";
    private final GenericDAO dao;

    private final String getClient;
    private final String insertClient;
    private final String countClientsByRut;
    
    public ClientJDBCAdapter(final GenericDAO dao) {
        this.dao = dao;
        this.getClient = SqlReader.read(SQL_GET_CLIENT);
        this.insertClient = SqlReader.read(SQL_INSERT_CLIENT);
        this.countClientsByRut = SqlReader.read(SQL_EXISTS_CLIENT_BY_RUT);
    }

    @Override
    public Integer createClient(Client client) {
        log.info("Insertando un nuevo cliente en la BD [{}]", client);

        int count = countClientsByRut(client.getRut());

        if (count > 0){
            log.error("Ya existe un cliente con el mismo RUT: [{}]", client.getRut());
            throw new DuplicatedClientException(ErrorCode.CLIENT_DUPLICATED);
        }

        var params = new MapSqlParameterSource()
            .addValue(KEY_CLIENT_RUT,client.getRut())
            .addValue(KEY_CLIENT_NAME, client.getName())
            .addValue(KEY_CLIENT_NATIONALITY, client.getNationality())
            .addValue(KEY_CLIENT_BIRTHDATE, client.getBirthDate());
        return dao.insert(insertClient,params, null).intValue();
    }

    @Override
    public Client getClientById(String rut) {
        var parameter = new MapSqlParameterSource().addValue(KEY_CLIENT_RUT, rut);
            log.info("Se va a realizar la busqueda del cliente cuyo rut es: [{}]", rut);
        var response = dao.findOne(getClient, parameter, ClientJDBCModel.class)
            .orElseThrow(()-> new BadRequestRestClientException(ErrorCode.CLIENT_NOT_FOUND));
        return response.toDomain();
    }

    @Override
    public Integer countClientsByRut (String rut) {
        var parameter = new MapSqlParameterSource().addValue(KEY_CLIENT_RUT, rut);
            log.info("Se va a realizar la verificación de si existe un cliente con el rut: [{}]", rut);
        
        try {
            var count = dao.queryForObject(countClientsByRut, parameter, Integer.class);
            return count != null ? ((Integer) count).intValue() : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

    @Override
    public Client getClient(String rut) {
        var parameter = new MapSqlParameterSource().addValue(KEY_CLIENT_RUT, rut);
        log.info("Se va a realizar la busqueda del cliente cuyo rut es: [{}]", rut);
        var response = dao.findOne(getClient, parameter, ClientJDBCModel.class)
                .orElseThrow(()-> new BadRequestRestClientException(ErrorCode.CLIENT_NOT_FOUND));
        return response.toDomain();
    }
}