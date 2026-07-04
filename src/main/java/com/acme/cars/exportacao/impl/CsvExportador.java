package com.acme.cars.exportacao.impl;

import com.acme.cars.exportacao.ExportadorArquivo;
import com.acme.cars.exportacao.TipoExportacao;
import com.acme.cars.model.Carro;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvExportador implements ExportadorArquivo {

    @Override
    public void exportar(List<Carro> carros, String caminhoArquivo) {

        try (CSVWriter writer = new CSVWriter(new FileWriter(caminhoArquivo))) {
            writer.writeNext(new String[]{"ID", "MODELO", "ANO", "COR", "HP", "FABRICANTE", "PAIS"});

            for (Carro carro : carros) {
                writer.writeNext(new String[]{
                        String.valueOf(carro.getId()),
                        carro.getModelo(),
                        String.valueOf(carro.getAno()),
                        carro.getCor(),
                        String.valueOf(carro.getCavalosDePotencia()),
                        carro.getFabricante(),
                        carro.getPais()
                });
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TipoExportacao getTipo() {
        return null;
    }
}
