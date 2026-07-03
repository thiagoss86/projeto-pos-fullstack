package com.acme.cars.repository.custom.pesquisa.strategy;

import com.acme.cars.dto.requests.BuscarCarroRequest;
import com.acme.cars.repository.custom.pesquisa.FiltroPesquisa;

import java.util.Optional;

public interface EstrategiaPesquisaCarro {

    Optional<FiltroPesquisa> criarFiltro(BuscarCarroRequest buscarCarroRequest);

}
