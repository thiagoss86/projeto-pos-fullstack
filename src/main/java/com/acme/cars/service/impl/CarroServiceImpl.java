package com.acme.cars.service.impl;

import com.acme.cars.dto.requests.BuscarCarroRequest;
import com.acme.cars.exception.RecursoNaoEncontradoException;
import com.acme.cars.model.Carro;
import com.acme.cars.observador.ObservadorCadastroCarro;
import com.acme.cars.repository.CarroRepository;
import com.acme.cars.service.CarroService;
import com.acme.cars.service.PesquisaCarroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarroServiceImpl implements CarroService {

    private final CarroRepository carroRepository;
    private final PesquisaCarroService pesquisaCarroService;
    private final List<ObservadorCadastroCarro> observadores;

    @Override
    public Carro buscarPorId(Long id) {

        return carroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Carro não encontrado com id: " + id));

    }

    @Override
    public Carro salvar(Carro carro) {

        Carro carroSalvo = carroRepository.save(carro);

        observadores.forEach(observador -> {
            observador.carroCadastrado(carroSalvo);
        });

        return carroSalvo;
    }

    @Override
    public void deletar(Long id) {

        Carro carro = buscarPorId(id);

        carroRepository.delete(carro);
    }

    @Override
    public Carro atualizar(Long id, Carro carroAtualizado) {

        Carro carroExistente = buscarPorId(id);

        carroAtualizado.setId(carroExistente.getId());

        return carroRepository.save(carroAtualizado);
    }

    @Override
    public long count() {

        return carroRepository.count();

    }

    @Override
    public List<Carro> listarTodos() {

        return carroRepository.findAll();

    }

    @Override
    public List<Carro> buscarTodosPaginado(int page, int size) {

        return carroRepository.findAll(PageRequest.of(page, size)).stream().toList();

    }

    @Override
    public List<Carro> buscar(BuscarCarroRequest buscarCarroRequest) {

        return pesquisaCarroService.buscar(buscarCarroRequest);

    }

}
