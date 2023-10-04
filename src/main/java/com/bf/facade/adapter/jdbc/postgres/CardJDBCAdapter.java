package com.bf.facade.adapter.jdbc.postgres;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import com.bf.facade.adapter.jdbc.dao.sql.GenericDAO;
import com.bf.facade.adapter.jdbc.dao.sql.SqlReader;
import com.bf.facade.adapter.jdbc.postgres.model.CardJDBCModel;
import com.bf.facade.adapter.rest.exception.BadRequestRestCardException;
import com.bf.facade.adapter.rest.exception.DuplicatedCardException;
import com.bf.facade.application.port.out.CardJDBCRepository;
import com.bf.facade.config.ErrorCode;
import com.bf.facade.domain.Card;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CardJDBCAdapter implements CardJDBCRepository {
    private static final String SQL_GET_CARD = "sql/get-card.sql";
    private static final String SQL_INSERT_CARD = "sql/insert-card.sql";
    private static final String SQL_EXISTS_CARD_BY_CARDNUMBER = "sql/exists-card-by-cardNumber.sql";
    private static final String KEY_CARD_ACCOUNT_NUMBER = "account";
    private static final String KEY_CARD_CARDNUMBER = "cardNumber";
    private static final String KEY_CARD_TYPE = "type";
    private static final String KEY_CARD_DESCRIPTIONTYPE = "descriptionType";
    private static final String KEY_CARD_STATUS = "status";
    private static final String KEY_CARD_DESCRIPTIONSTATUS = "descriptionStatus";

    private final GenericDAO dao;

    private final String getCard;
    private final String insertCard;
    private final String countCardsByCardNumber;

    public CardJDBCAdapter(final GenericDAO dao) {
        this.dao = dao;
        this.getCard = SqlReader.read(SQL_GET_CARD);
        this.insertCard = SqlReader.read(SQL_INSERT_CARD);
        this.countCardsByCardNumber = SqlReader.read(SQL_EXISTS_CARD_BY_CARDNUMBER);
    }

    @Override
    public Integer createCard(Card card) {
        log.info("Insertando una nueva tarjeta en la BD [{}]", card);

        int count = countCardByCardNumber(card.getCardNumber());

        if (count > 0){
            log.error("Ya existe una tarjeta con el mismo numero de tarjeta: [{}]", card.getCardNumber());
            throw new DuplicatedCardException(ErrorCode.CARD_DUPLICATED);
        }

        var params = new MapSqlParameterSource()
                .addValue(KEY_CARD_ACCOUNT_NUMBER, card.getAccount())
                .addValue(KEY_CARD_CARDNUMBER, card.getCardNumber())
                .addValue(KEY_CARD_TYPE, card.getType())
                .addValue(KEY_CARD_DESCRIPTIONTYPE, card.getDescriptionType())
                .addValue(KEY_CARD_STATUS, card.getStatus())
                .addValue(KEY_CARD_DESCRIPTIONSTATUS, card.getDescriptionStatus());
        return dao.insert(insertCard, params, null).intValue();

    }

    @Override
    public Card getCardByCardNumber (String cardNumber) {
        var parameter = new MapSqlParameterSource().addValue(KEY_CARD_CARDNUMBER, cardNumber);
            log.info("Se va a realizar la busqueda de la tarejta cuyo numero de tarjeta es: [{}]", cardNumber);
        var response = dao.findOne(getCard, parameter, CardJDBCModel.class)
            .orElseThrow(()-> new BadRequestRestCardException(ErrorCode.CARD_NOT_FOUND));
        return response.toDomain();
    }

    @Override
    public Integer countCardByCardNumber (String cardNumber) {
        var parameter = new MapSqlParameterSource().addValue(KEY_CARD_CARDNUMBER, cardNumber);
            log.info("Se va a realizar la verificación de si existe una tarjeta con el numero de tarjeta: [{}]", cardNumber);
        
        try {
            var count = dao.queryForObject(countCardsByCardNumber, parameter, Integer.class);
            return count != null ? ((Integer) count).intValue() : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }
}