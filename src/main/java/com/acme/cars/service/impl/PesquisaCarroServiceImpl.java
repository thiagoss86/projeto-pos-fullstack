package com.acme.cars.service.impl;

import com.acme.cars.dto.requests.BuscarCarroRequest;
import com.acme.cars.model.Carro;
import com.acme.cars.service.PesquisaCarroService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PesquisaCarroServiceImpl implements PesquisaCarroService {

    private final EntityManager entityManager;

    @Override
    public List<Carro> buscar(BuscarCarroRequest buscarCarroRequest) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Carro> cq = cb.createQuery(Carro.class);

        Root<Carro> carro = cq.from(Carro.class);

        List<Predicate> predicates = new ArrayList<>();

        if (buscarCarroRequest.modelo().isPresent()) {
            predicates.add(cb.like(cb.lower(carro.get("modelo")), "%" + buscarCarroRequest.modelo().get().toLowerCase() + "%"));
        }

        if (buscarCarroRequest.fabricante().isPresent()) {
            predicates.add(cb.like(cb.lower(carro.get("fabricante")), "%" + buscarCarroRequest.fabricante().get().toLowerCase() + "%"));
        }

        if (buscarCarroRequest.pais().isPresent()) {
            predicates.add(cb.like(cb.lower(carro.get("pais")), "%" + buscarCarroRequest.pais().get().toLowerCase() + "%"));
        }

        cq.where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(cq).getResultList();
    }
}
