package com.acme.cars.adaptador;

import com.acme.cars.model.Carro;

public interface AdaptadorVeiculo {

    Carro adaptar(VeiculoLegado veiculoLegado);

}
