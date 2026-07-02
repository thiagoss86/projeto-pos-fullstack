package com.acme.cars.repository;

import com.acme.cars.model.TokenRevogado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface RevokedTokenRepository extends JpaRepository<TokenRevogado, String> {
    long deleteByExpiresAtBefore(Instant now);
}
