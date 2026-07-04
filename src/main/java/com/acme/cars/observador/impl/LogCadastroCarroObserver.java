package com.acme.cars.observador.impl;

import com.acme.cars.model.Carro;
import com.acme.cars.observador.ObservadorCadastroCarro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogCadastroCarroObserver implements ObservadorCadastroCarro {

    @Override
    public void carroCadastrado(Carro carro) {

        log.info("Carro {} cadastrado com sucesso", carro.getModelo());

    }

}
