package com.acme.cars.service;

import com.acme.cars.model.Usuario;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface TokenService {
    String gerarToken(Usuario usuario);

    DecodedJWT isValid(String token);
}
