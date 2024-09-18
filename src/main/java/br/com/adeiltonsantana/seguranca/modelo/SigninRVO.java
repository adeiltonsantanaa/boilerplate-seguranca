package br.com.adeiltonsantana.seguranca.modelo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SigninRVO(
        @NotBlank(message = "Nome não pode ser vazio")
        String nome,
        
        @NotBlank(message = "Sobrenome não pode ser vazio")
        String sobrenome,
        
        @NotBlank(message = "E-mail não pode ser vazio")
        @Email(message = "E-mail inválido")
        String email,
        
        @NotBlank(message = "Senha não pode ser vazia")
        String senha
) {}
