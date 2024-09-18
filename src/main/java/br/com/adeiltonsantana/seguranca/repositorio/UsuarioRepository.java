package br.com.adeiltonsantana.seguranca.repositorio;

import br.com.adeiltonsantana.seguranca.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNomeUsuario(String nomeUsuario);
}
