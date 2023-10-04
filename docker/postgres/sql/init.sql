CREATE TABLE IF NOT EXISTS client
(
    id_client SERIAL PRIMARY KEY,
    rut VARCHAR(100) not null UNIQUE,
    name VARCHAR(100) not null,
    nationality INTEGER not null,
    birthDate VARCHAR(100) not null
);

CREATE TABLE IF NOT EXISTS account
(
    id_account SERIAL PRIMARY KEY,
    account_number INTEGER not null UNIQUE,
    balance NUMERIC not null
);

CREATE TABLE IF NOT EXISTS client_card
(
    id_client_card SERIAL PRIMARY KEY,
    id_client SERIAL,
    id_card SERIAL UNIQUE,
    id_additional SERIAL,
    FOREIGN KEY (id_client) REFERENCES client(id_client)
);

CREATE TABLE IF NOT EXISTS card
(
    id_card SERIAL PRIMARY KEY,
    account_number INTEGER not null ,
    card_number VARCHAR not null,
    type NUMERIC not null,
    description_type VARCHAR not null,
    status NUMERIC not null,
    description_status VARCHAR not null,
    FOREIGN KEY (account_number) REFERENCES account(account_number)
);

