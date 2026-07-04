package com.acme.cars.service;

import com.acme.cars.adaptador.VeiculoLegado;
import com.acme.cars.model.Carro;

public interface ImportacaoVeiculoService {

    Carro importar(VeiculoLegado legado);

}
