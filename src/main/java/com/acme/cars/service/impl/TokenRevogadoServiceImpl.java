package com.acme.cars.service.impl;

import com.acme.cars.model.TokenRevogado;
import com.acme.cars.repository.RevokedTokenRepository;
import com.acme.cars.service.TokenRevogadoService;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenRevogadoServiceImpl implements TokenRevogadoService {

    private final RevokedTokenRepository repo;
    private final TokenServiceImpl tokenServiceImpl;

    @Override
    public boolean isRevogado(String jti) {
        limparTokensExpirados();
        return jti != null && repo.existsById(jti);
    }

    @Override
    public void revogar(String authorizationHeader) {
        DecodedJWT jwt = tokenServiceImpl.isValid(authorizationHeader);
        String jti = tokenServiceImpl.getJti(jwt);
        Instant exp = tokenServiceImpl.getExpiresAt(jwt).toInstant();

        repo.save(TokenRevogado.builder()
                .jti(jti)
                .expiresAt(exp)
                .build());
    }

    private void limparTokensExpirados() {
        repo.deleteByExpiresAtBefore(Instant.now());
    }
}
