package com.acme.cars.service;

import com.acme.cars.dto.requests.BuscarCarroRequest;
import com.acme.cars.model.Carro;

import java.util.List;

public interface CarroService {

    Carro buscarPorId(Long id);

    Carro salvar(Carro carro);

    void deletar(Long id);

    Carro atualizar(Long id, Carro carroAtualizado);

    long count();

    List<Carro> listarTodos();

    List<Carro> buscarTodosPaginado(int page, int size);

    List<Carro> buscar(BuscarCarroRequest buscarCarroRequest);
}
