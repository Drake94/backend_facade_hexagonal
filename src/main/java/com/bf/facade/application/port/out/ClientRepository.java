package com.bf.facade.application.port.out;

import com.bf.facade.domain.Client;

public interface ClientRepository {
    Client getClient(String rut);
    boolean existsClientByRut(String rut);
}
