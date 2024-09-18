package br.com.adeiltonsantana.seguranca.servico;

import br.com.adeiltonsantana.seguranca.entidade.Usuario;
import br.com.adeiltonsantana.seguranca.excecoes.UsuarioSenhaInvalidoExcecao;
import br.com.adeiltonsantana.seguranca.modelo.JWTRVO;
import br.com.adeiltonsantana.seguranca.modelo.LoginRVO;
import br.com.adeiltonsantana.seguranca.modelo.SigninRVO;
import br.com.adeiltonsantana.seguranca.repositorio.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoServico {

    private final TokenServico tokenServico;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoServico(TokenServico tokenServico, AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.tokenServico = tokenServico;
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public JWTRVO login(LoginRVO loginRVO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRVO.nomeUsuario(), loginRVO.senha()));
            return tokenServico.codificarToken(loginRVO.nomeUsuario());
        } catch (AuthenticationException e) {
            throw new UsuarioSenhaInvalidoExcecao("Usuário ou senha inválidos");
        }
    }

    public void salvarUsuario(SigninRVO signinRVO) {
        usuarioRepository.findByNomeUsuario(signinRVO.email()).ifPresent(usuario -> {
            throw new IllegalArgumentException("O usuário já existe");
        });
        usuarioRepository.save(converterParaUsuario(signinRVO));
    }

    private Usuario converterParaUsuario(SigninRVO signinRVO) {
        return new Usuario(signinRVO.email(), passwordEncoder.encode(signinRVO.senha()), signinRVO.nome(), signinRVO.sobrenome());
    }

}