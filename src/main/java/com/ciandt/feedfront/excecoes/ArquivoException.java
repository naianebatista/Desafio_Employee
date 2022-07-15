package com.ciandt.feedfront.excecoes;

import java.io.IOException;

public class ArquivoException extends IOException {
    public ArquivoException(String message) {
        super(message);
    }
}
