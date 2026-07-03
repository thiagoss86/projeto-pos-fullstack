package com.acme.cars.repository.custom;

import com.acme.cars.dto.requests.BuscarCarroRequest;
import com.acme.cars.model.Carro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CarroPesquisaRepositoryImpl implements CarroPesquisaRepository {

    private final EntityManager em;

    @Override
    public List<Carro> buscar(BuscarCarroRequest request) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Carro> cq = cb.createQuery(Carro.class);

        Root<Carro> carro = cq.from(Carro.class);

        List<Predicate> predicates = new ArrayList<>();

        cq.where(predicates.toArray(Predicate[]::new));

        return em.createQuery(cq).getResultList();
    }
}
