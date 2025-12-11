package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {
    Optional<Coordenador> findByPessoaId(Long pessoaId);
    List<Coordenador> findByIgrejaId(Long igrejaId);
}
