package br.com.adeiltonsantana.seguranca.excecoes;

public class ErroGenericoExcecao extends RuntimeException {
    public ErroGenericoExcecao(String message) {
        super(message);
    }
}
