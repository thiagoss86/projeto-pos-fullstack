package com.acme.cars.exportacao;

import com.acme.cars.exception.ExportadorNaoEncontradoException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FabricaExportador {

    private final Map<TipoExportacao, ExportadorArquivo> exportadores;

    public FabricaExportador(List<ExportadorArquivo> listaExportadores) {

        this.exportadores =
                listaExportadores.stream()
                        .collect(Collectors.toUnmodifiableMap(
                                ExportadorArquivo::tipo,
                                Function.identity()));
    }

    public ExportadorArquivo obter(TipoExportacao tipoExportacao) {

        return Optional.ofNullable(exportadores.get(tipoExportacao))
                .orElseThrow(() -> new ExportadorNaoEncontradoException(tipoExportacao));

    }
}
