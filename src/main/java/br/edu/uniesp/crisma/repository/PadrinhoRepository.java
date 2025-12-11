package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Padrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PadrinhoRepository extends JpaRepository<Padrinho, Long> {
    Optional<Padrinho> findByPessoaId(Long pessoaId);
}
