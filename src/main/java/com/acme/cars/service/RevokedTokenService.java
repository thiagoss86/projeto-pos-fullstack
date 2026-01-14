package com.acme.cars.service;

import com.acme.cars.model.RevokedToken;
import com.acme.cars.repository.RevokedTokenRepository;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RevokedTokenService {

    private final RevokedTokenRepository repo;
    private final TokenService tokenService;

    public boolean isRevoked(String jti) {
        cleanupExpired();
        return jti != null && repo.existsById(jti);
    }

    public void revoke(String authorizationHeader) {
        DecodedJWT jwt = tokenService.isValid(authorizationHeader);
        String jti = tokenService.getJti(jwt);
        Instant exp = tokenService.getExpiresAt(jwt).toInstant();

        repo.save(RevokedToken.builder()
                .jti(jti)
                .expiresAt(exp)
                .build());
    }

    private void cleanupExpired() {
        repo.deleteByExpiresAtBefore(Instant.now());
    }
}
