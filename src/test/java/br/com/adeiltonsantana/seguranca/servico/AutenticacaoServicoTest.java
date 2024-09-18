package br.com.adeiltonsantana.seguranca.servico;

import br.com.adeiltonsantana.seguranca.entidade.Usuario;
import br.com.adeiltonsantana.seguranca.modelo.SigninRVO;
import br.com.adeiltonsantana.seguranca.repositorio.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AutenticacaoServicoTest {

    private AutenticacaoServico autenticacaoServico;
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        TokenServico tokenInterface = mock(TokenServico.class);
        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);
        usuarioRepository = mock(UsuarioRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        autenticacaoServico = new AutenticacaoServico(tokenInterface, authenticationManager, usuarioRepository, passwordEncoder);
    }

    @Test
    @DisplayName("Testa salvarUsuario com nome vazio")
    void salvarUsuario1() {
        SigninRVO signinRVO = new SigninRVO("", "Sobrenome", "email@example.com", "senha");
        assertThrows(IllegalArgumentException.class, () -> autenticacaoServico.salvarUsuario(signinRVO));
    }

    @Test
    @DisplayName("Testa salvarUsuario com sobrenome vazio")
    void salvarUsuario2() {
        SigninRVO signinRVO = new SigninRVO("Nome", "", "email@example.com", "senha");
        assertThrows(IllegalArgumentException.class, () -> autenticacaoServico.salvarUsuario(signinRVO));
    }

    @Test
    @DisplayName("Testa salvarUsuario com email vazio")
    void salvarUsuario3() {
        SigninRVO signinRVO = new SigninRVO("Nome", "Sobrenome", "", "senha");
        assertThrows(IllegalArgumentException.class, () -> autenticacaoServico.salvarUsuario(signinRVO));
    }

    @Test
    @DisplayName("Testa salvarUsuario com senha vazia")
    void salvarUsuario4() {
        SigninRVO signinRVO = new SigninRVO("Nome", "Sobrenome", "email@example.com", "");
        assertThrows(IllegalArgumentException.class, () -> autenticacaoServico.salvarUsuario(signinRVO));
    }

    @Test
    @DisplayName("Testa salvarUsuario com email já existente")
    void salvarUsuario5() {
        SigninRVO signinRVO = new SigninRVO("Nome", "Sobrenome", "email@example.com", "senha");
        when(usuarioRepository.findByNomeUsuario(signinRVO.email())).thenReturn(Optional.of(new Usuario()));
        assertThrows(IllegalArgumentException.class, () -> autenticacaoServico.salvarUsuario(signinRVO));
    }

    @Test
    @DisplayName("Testa salvarUsuario com email não existente")
    void salvarUsuario6() {
        SigninRVO signinRVO = new SigninRVO("Nome", "Sobrenome", "email@example.com", "senha");
        when(usuarioRepository.findByNomeUsuario(signinRVO.email())).thenReturn(Optional.empty());
        autenticacaoServico.salvarUsuario(signinRVO);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
}