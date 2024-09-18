package br.com.adeiltonsantana.seguranca.repositorio;

import br.com.adeiltonsantana.seguranca.entidade.Papel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PapelRepository extends JpaRepository<Papel, Long> {
}
