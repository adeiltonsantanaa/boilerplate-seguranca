package br.com.adeiltonsantana.seguranca.modelo;

import java.util.Date;

public record JWTRVO(String token, String tipo, Date criacao, Date expiracao) {
}
