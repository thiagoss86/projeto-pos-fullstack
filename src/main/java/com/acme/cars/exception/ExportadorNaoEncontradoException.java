package com.acme.cars.exception;

import com.acme.cars.exportacao.TipoExportacao;

public class ExportadorNaoEncontradoException extends RuntimeException {

    public ExportadorNaoEncontradoException(TipoExportacao tipo) {

        super("Nenhum exportador encontrado para o tipo: " + tipo);

    }
}
