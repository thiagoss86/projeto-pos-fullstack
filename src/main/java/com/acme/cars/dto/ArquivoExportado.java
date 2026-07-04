package com.acme.cars.dto;

import org.springframework.http.MediaType;

public record ArquivoExportado(
        byte[] conteudo,
        String nomeArquivo,
        MediaType mediaType
) { }
