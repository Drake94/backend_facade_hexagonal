package com.bf.facade.application.port.in;

import java.util.concurrent.ExecutionException;

import com.bf.facade.domain.Client;

public interface GetClient {
    Client getClient(String rut) throws ExecutionException, InterruptedException;
    Client getInternal(String rut);
}