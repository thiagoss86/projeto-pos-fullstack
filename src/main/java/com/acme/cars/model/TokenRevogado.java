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
@Table(name = "token_revogado")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenRevogado {
    @Id
    private String jti;
    private Instant expiresAt;
}
