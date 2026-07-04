package com.acme.cars.adaptador.impl;

import com.acme.cars.adaptador.AdaptadorVeiculo;
import com.acme.cars.adaptador.VeiculoLegado;
import com.acme.cars.model.Carro;
import org.springframework.stereotype.Component;

@Component
public class AdaptadorVeiculoImpl implements AdaptadorVeiculo {

    @Override
    public Carro adaptar(VeiculoLegado legado) {

        Carro carro = new Carro();

        carro.setModelo(legado.getCarModel());

        carro.setFabricante(legado.getCarModel());

        carro.setAno(legado.getManufactureYeah());

        carro.setCor(legado.getPaint());

        carro.setPais(legado.getOriginCountry());

        carro.setCavalosDePotencia(legado.getHorsePower());

        return carro;

    }
}
