package br.com.adeiltonsantana.seguranca.modelo;

import java.util.Date;

public record ErroRVO(String mensagem, String detalhe, Date dataHora) {

    public ErroRVO(String mensagem, String detalhe) {
        this(mensagem, detalhe, new Date());
    }
}
