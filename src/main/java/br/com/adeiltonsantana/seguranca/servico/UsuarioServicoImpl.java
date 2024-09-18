package br.com.adeiltonsantana.seguranca.servico;

import br.com.adeiltonsantana.seguranca.excecoes.UsuarioNaoEncontradoExcecao;
import br.com.adeiltonsantana.seguranca.repositorio.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicoImpl implements UserDetailsService {

    private final UsuarioRepository userRepository;

    public UsuarioServicoImpl(UsuarioRepository userRepository ) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsuarioNaoEncontradoExcecao {
        return userRepository.findByNomeUsuario(username)
                .orElseThrow(() -> new UsuarioNaoEncontradoExcecao("Usuário não encontrado"));
    }
}