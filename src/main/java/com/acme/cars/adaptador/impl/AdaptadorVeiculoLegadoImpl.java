package com.acme.cars.adaptador.impl;

import com.acme.cars.adaptador.AdaptadorVeiculoLegado;
import com.acme.cars.adaptador.VeiculoLegado;
import com.acme.cars.model.Carro;
import org.springframework.stereotype.Component;

@Component
public class AdaptadorVeiculoLegadoImpl implements AdaptadorVeiculoLegado {

    @Override
    public Carro adaptar(VeiculoLegado legado) {

        Carro carro = new Carro();

        carro.setModelo(legado.carModel());

        carro.setFabricante(legado.maker());

        carro.setAno(legado.manufactureYeah());

        carro.setCor(legado.paint());

        carro.setPais(legado.originCountry());

        carro.setCavalosDePotencia(legado.horsePower());

        return carro;

    }
}
