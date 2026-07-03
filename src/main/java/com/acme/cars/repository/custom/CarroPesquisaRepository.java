package com.acme.cars.repository.custom;

import com.acme.cars.dto.requests.BuscarCarroRequest;
import com.acme.cars.model.Carro;

import java.util.List;

public interface CarroPesquisaRepository {

    List<Carro> buscar(BuscarCarroRequest request);
}
