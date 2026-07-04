package com.acme.cars.exportacao.impl;

import com.acme.cars.dto.ArquivoExportado;
import com.acme.cars.exportacao.ExportadorArquivo;
import com.acme.cars.exportacao.TipoExportacao;
import com.acme.cars.model.Carro;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CsvExportador implements ExportadorArquivo {


    @Override
    public TipoExportacao tipo() {
        return TipoExportacao.CSV;
    }

    @Override
    public ArquivoExportado exportar(List<Carro> carros) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (
                OutputStreamWriter outputStreamWriter =
                        new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

                CSVWriter writer = new CSVWriter(outputStreamWriter)
        ) {

            writer.writeNext(new String[]{
                    "ID",
                    "MODELO",
                    "ANO",
                    "COR",
                    "HP",
                    "FABRICANTE",
                    "PAIS"
            });

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

            writer.flush();

            return new ArquivoExportado(
                    outputStream.toByteArray(),
                    "carros.csv",
                    MediaType.parseMediaType("text/csv")
            );

        } catch (IOException e) {

            throw new RuntimeException("Erro ao gerar arquivo CSV", e);

        }
    }
}
