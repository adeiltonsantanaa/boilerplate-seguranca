package br.com.adeiltonsantana.seguranca.repositorio;

import br.com.adeiltonsantana.seguranca.entidade.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
}
