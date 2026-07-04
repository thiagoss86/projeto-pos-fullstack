package com.acme.cars.exportacao;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FabricaExportador {

    private final Map<TipoExportacao, ExportadorArquivo> exportadores;

    public FabricaExportador(List<ExportadorArquivo> listaExportadores) {

        exportadores =
                listaExportadores.stream()
                        .collect(Collectors.toUnmodifiableMap(
                                ExportadorArquivo::getTipo,
                                Function.identity()));
    }
}
