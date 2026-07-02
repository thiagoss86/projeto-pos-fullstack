package com.acme.cars.service;

public interface TokenRevogadoService {

    boolean isRevogado(String jti);

    void revogar(String authorizationHeader);
}
