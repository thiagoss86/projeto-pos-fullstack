package com.acme.cars.adaptador;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeiculoLegado {

    private String carModel;
    private String maker;
    private Integer horsePower;
    private Integer manufactureYeah;
    private String paint;
    private String originCountry;

}
