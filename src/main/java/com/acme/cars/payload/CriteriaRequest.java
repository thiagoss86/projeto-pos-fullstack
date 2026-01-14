package com.acme.cars.payload;

import java.util.Optional;

/**
 * Critérios opcionais para filtro de carros.
 */
public record CriteriaRequest(Optional<String> modelo,
                             Optional<String> fabricante,
                             Optional<String> pais) {
}
