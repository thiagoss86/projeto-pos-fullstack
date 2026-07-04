package com.acme.cars.repository.custom.pesquisa.estrategias;

import com.acme.cars.constant.CamposCarro;
import com.acme.cars.dto.requests.BuscarCarroRequest;
import com.acme.cars.repository.custom.pesquisa.FiltroPesquisa;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PesquisaModeloStrategy implements EstrategiaPesquisaCarro {

    @Override
    public Optional<FiltroPesquisa> aplicarFiltro(BuscarCarroRequest buscarCarroRequest) {

        return buscarCarroRequest.modelo()
                .map(modelo ->
                        new FiltroPesquisa(
                                CamposCarro.MOLEDO,
                                modelo
                        ));

    }

}
