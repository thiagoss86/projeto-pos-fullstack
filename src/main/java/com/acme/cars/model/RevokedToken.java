package com.acme.cars.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "revoked_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RevokedToken {
    @Id
    private String jti;
    private Instant expiresAt;
}
