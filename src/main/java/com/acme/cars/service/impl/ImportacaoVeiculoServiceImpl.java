package com.acme.cars.service.impl;

import com.acme.cars.adaptador.AdaptadorVeiculoLegado;
import com.acme.cars.adaptador.VeiculoLegado;
import com.acme.cars.model.Carro;
import com.acme.cars.service.CarroService;
import com.acme.cars.service.ImportacaoVeiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImportacaoVeiculoServiceImpl implements ImportacaoVeiculoService {

    private final AdaptadorVeiculoLegado adaptadorVeiculoLegado;

    private final CarroService carroService;

    public Carro importar(VeiculoLegado legado) {

        Carro carro = adaptadorVeiculoLegado.adaptar(legado);

        return carroService.salvar(carro);

    }

}
