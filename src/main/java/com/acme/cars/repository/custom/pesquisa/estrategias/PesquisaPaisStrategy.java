package com.acme.cars.repository.custom.pesquisa.estrategias;

import com.acme.cars.constant.CamposCarro;
import com.acme.cars.dto.requests.BuscarCarroRequest;
import com.acme.cars.repository.custom.pesquisa.FiltroPesquisa;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PesquisaPaisStrategy implements EstrategiaPesquisaCarro {

    @Override
    public Optional<FiltroPesquisa> aplicarFiltro(BuscarCarroRequest buscarCarroRequest) {
        return buscarCarroRequest.modelo()
                .map(modelo ->
                        new FiltroPesquisa(
                                CamposCarro.PAIS,
                                modelo
                        ));

    }

}
