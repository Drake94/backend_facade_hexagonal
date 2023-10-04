package com.bf.facade.adapter.jdbc.postgres.model;

import java.io.Serializable;

import com.bf.facade.domain.Card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardJDBCModel implements Serializable {

    private Integer account;
    private String cardNumber;
    private Integer type;
    private String descriptionType;
    private Integer status;
    private String descriptionStatus;

    public Card toDomain() {
        return Card.builder()
                .account(this.account)
                .cardNumber(this.cardNumber)
                .type(this.type)
                .descriptionType(this.descriptionType)
                .status(this.status)
                .descriptionStatus(this.descriptionStatus)
                .build();
    }

}