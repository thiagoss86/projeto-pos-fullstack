package com.acme.cars.dto.requests;

import java.util.Optional;

/**
 * Critérios opcionais para filtro de carros.
 */
public record BuscarCarroRequest(Optional<String> modelo,
                                 Optional<String> fabricante,
                                 Optional<String> pais) {
}
