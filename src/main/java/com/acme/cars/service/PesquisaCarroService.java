package com.acme.cars.service;

import com.acme.cars.dto.requests.BuscarCarroRequest;
import com.acme.cars.model.Carro;

import java.util.List;

public interface PesquisaCarroService {

    List<Carro> buscar(BuscarCarroRequest buscarCarroRequest);

}
