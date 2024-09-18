package br.com.adeiltonsantana.seguranca.excecoes;

public class UsuarioSenhaInvalidoExcecao extends RuntimeException {
    public UsuarioSenhaInvalidoExcecao(String message) {
        super(message);
    }
}
