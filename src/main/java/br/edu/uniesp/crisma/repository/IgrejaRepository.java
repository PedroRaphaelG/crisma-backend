package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Igreja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IgrejaRepository extends JpaRepository<Igreja, Long> {
    Optional<Igreja> findByNome(String nome);
    Optional<Igreja> findByCnpj(String cnpj);
    List<Igreja> findByAtivoTrue();
    boolean existsByCnpj(String cnpj);
}
