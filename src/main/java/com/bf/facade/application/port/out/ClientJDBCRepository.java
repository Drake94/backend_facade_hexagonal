package com.bf.facade.application.port.out;

import com.bf.facade.domain.Client;

public interface ClientJDBCRepository {
    Integer createClient(Client client);
    Client getClientById(String rut);
    Integer countClientsByRut(String rut);
    Client getClient(String rut);
}
