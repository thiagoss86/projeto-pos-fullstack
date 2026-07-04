package com.acme.cars.exportacao;

import com.acme.cars.model.Carro;

import java.util.List;

public interface ExportadorArquivo {

    TipoExportacao getTipo();

    void exportar(
            List<Carro> carros,
            String caminhoArquivo);

}
