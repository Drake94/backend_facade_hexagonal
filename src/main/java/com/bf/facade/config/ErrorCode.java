package com.bf.facade.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_ERROR(100, "Error interno del servidor"),
    WEB_CLIENT_GENERIC(101, "Error del Web client"),
    CLIENT_NOT_FOUND(102, "No se encontro el cliente"),
    CLIENT_BAD_REQUEST(103, "El llamado al cliente retorno una peticion invalida"),
    CLIENT_TIMEOUT(104, "El llamado al cliente devolvio no respondio a tiempo"),
    CLIENT_DUPLICATED(105, "Ya existe un cliente con el mismo RUT"),
    ACCOUNT_NOT_FOUND(106, "No se encontro la cuenta"),
    ACCOUNT_BAD_REQUEST(107,"El llamado a la cuenta retorno una peticion invalida"),
    ACCOUNT_TIMEOUT(108,"El llamado a la cuenta no respondio a tiempo"),
    ACCOUNT_DUPLICATED(109,"Ya existe una cuenta con el mismo numero de cuenta"),
    CARD_NOT_FOUND(110, "No se encontro la tarjeta"),
    CARD_BAD_REQUEST(111,"El llamado a la tarjeta retorno una peticion invalida"),
    CARD_TIMEOUT(112,"El llamado a la tarjeta no respondio a tiempo"),
    CARD_DUPLICATED(113,"Ya existe una tarjeta con el mismo numero de tarjeta");
    
    private final int value;
    private final String reason;

 }
