package com.paineltarefas.api.model;

public class Erro {

    private String mensagemErro;

    public Erro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }
}
