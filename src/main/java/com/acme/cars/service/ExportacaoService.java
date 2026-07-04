package com.acme.cars.service;

import com.acme.cars.dto.ArquivoExportado;
import com.acme.cars.exportacao.TipoExportacao;

public interface ExportacaoService {

    ArquivoExportado exportar(TipoExportacao tipo);
}
