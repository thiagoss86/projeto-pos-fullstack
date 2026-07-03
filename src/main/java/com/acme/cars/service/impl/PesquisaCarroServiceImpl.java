package com.acme.cars.service.impl;

import com.acme.cars.dto.requests.BuscarCarroRequest;
import com.acme.cars.model.Carro;
import com.acme.cars.repository.custom.CarroPesquisaRepository;
import com.acme.cars.service.PesquisaCarroService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisaCarroServiceImpl implements PesquisaCarroService {

    private final CarroPesquisaRepository carroPesquisaRepository;

    @Override
    public List<Carro> buscar(BuscarCarroRequest buscarCarroRequest) {

        return carroPesquisaRepository.buscar(buscarCarroRequest);
    }
}
