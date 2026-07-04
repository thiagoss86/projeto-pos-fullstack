package com.acme.cars.exportacao;

import com.acme.cars.dto.ArquivoExportado;
import com.acme.cars.model.Carro;

import java.util.List;

public interface ExportadorArquivo {

    TipoExportacao tipo();

    ArquivoExportado exportar(List<Carro> carros);

}
