package com.acme.cars.service.impl;

import com.acme.cars.dto.ArquivoExportado;
import com.acme.cars.exportacao.ExportadorArquivo;
import com.acme.cars.exportacao.FabricaExportador;
import com.acme.cars.exportacao.TipoExportacao;
import com.acme.cars.model.Carro;
import com.acme.cars.service.CarroService;
import com.acme.cars.service.ExportacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportacaoServiceImpl implements ExportacaoService {

    private final CarroService carroService;

    private final FabricaExportador fabricaExportador;

    @Override
    public ArquivoExportado exportar(TipoExportacao tipo) {

        List<Carro> carros = carroService.listarTodos();

        ExportadorArquivo exportadorArquivo = fabricaExportador.obter(tipo);

        return exportadorArquivo.exportar(carros);
    }

}
