package br.com.adeiltonsantana.seguranca.excecoes;

public class TokenExpiradoExcecao extends RuntimeException {
    public TokenExpiradoExcecao(String message) {
        super(message);
    }
}
