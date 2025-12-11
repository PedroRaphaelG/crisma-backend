package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Catequista;
import br.edu.uniesp.crisma.enums.StatusCatequista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatequistaRepository extends JpaRepository<Catequista, Long> {
    Optional<Catequista> findByPessoaId(Long pessoaId);
    List<Catequista> findByStatus(StatusCatequista status);
    List<Catequista> findByExperienciaAnosGreaterThanEqual(Integer anos);
}
