package com.acme.cars.adaptador;

public record VeiculoLegado(
        String carModel,
        String maker,
        Integer horsePower,
        Integer manufactureYeah,
        String paint,
        String originCountry
) {
}
