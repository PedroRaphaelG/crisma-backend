package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Pessoa;
import br.edu.uniesp.crisma.enums.TipoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByCpf(String cpf);
    Optional<Pessoa> findByEmail(String email);
    List<Pessoa> findByTipoPessoa(TipoPessoa tipoPessoa);
    List<Pessoa> findByAtivoTrue();
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
