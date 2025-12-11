package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Caixinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaixinhaRepository extends JpaRepository<Caixinha, Long> {
    List<Caixinha> findByGrupoId(Long grupoId);
    Optional<Caixinha> findByGrupoIdAndMesReferenciaAndAnoReferencia(Long grupoId, Integer mes, Integer ano);
    List<Caixinha> findByAnoReferencia(Integer ano);
}
