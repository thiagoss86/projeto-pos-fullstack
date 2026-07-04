package com.acme.cars.repository.custom.pesquisa.estrategias;

import com.acme.cars.dto.requests.BuscarCarroRequest;
import com.acme.cars.repository.custom.pesquisa.FiltroPesquisa;

import java.util.Optional;

public interface EstrategiaPesquisaCarro {

    Optional<FiltroPesquisa> aplicarFiltro(BuscarCarroRequest buscarCarroRequest);

}
