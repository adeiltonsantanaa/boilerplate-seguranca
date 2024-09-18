package br.com.adeiltonsantana.seguranca.excecoes;

public class UsuarioNaoEncontradoExcecao extends RuntimeException {
    public UsuarioNaoEncontradoExcecao(String message) {
        super(message);
    }
}
