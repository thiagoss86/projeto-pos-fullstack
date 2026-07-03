package com.acme.cars.repository.custom;

import com.acme.cars.dto.requests.BuscarCarroRequest;
import com.acme.cars.model.Carro;
import com.acme.cars.repository.custom.pesquisa.FiltroPesquisa;
import com.acme.cars.repository.custom.pesquisa.strategy.EstrategiaPesquisaCarro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CarroPesquisaRepositoryImpl implements CarroPesquisaRepository {

    private final EntityManager em;

    private final List<EstrategiaPesquisaCarro> estrategias;

    @Override
    public List<Carro> buscar(BuscarCarroRequest request) {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Carro> cq = cb.createQuery(Carro.class);

        Root<Carro> carro = cq.from(Carro.class);

        List<Predicate> predicates = estrategias.stream()
                .map(estrategia ->
                        estrategia.criarFiltro(request))
                .flatMap(Optional::stream)
                .map(filtro -> criarPredicateLike(cb, carro, filtro))
                .toList();

        cq.where(predicates.toArray(Predicate[]::new));

        return em.createQuery(cq).getResultList();
    }

    private Predicate criarPredicateLike(
            CriteriaBuilder criteriaBuilder,
            Root<Carro> root,
            FiltroPesquisa filtroPesquisa) {

        return criteriaBuilder.like(
                criteriaBuilder.lower(root.get(filtroPesquisa.campo())),
                "%" + filtroPesquisa.valor().toLowerCase() + "%");
    }
}
