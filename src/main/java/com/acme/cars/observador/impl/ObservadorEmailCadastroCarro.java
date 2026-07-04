package com.acme.cars.observador.impl;

import com.acme.cars.model.Carro;
import com.acme.cars.observador.ObservadorCadastroCarro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ObservadorEmailCadastroCarro implements ObservadorCadastroCarro {

    @Override
    public void carroCadastrado(Carro carro) {

        log.info("Simulação de envio de e-mail para cadastro do carro {}", carro.getModelo());

    }
}
