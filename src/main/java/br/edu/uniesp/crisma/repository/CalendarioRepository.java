package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Calendario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarioRepository extends JpaRepository<Calendario, Long> {
    List<Calendario> findByGrupoId(Long grupoId);
    Optional<Calendario> findByGrupoIdAndSemana(Long grupoId, Integer semana);
    List<Calendario> findByGrupoIdOrderBySemanaAsc(Long grupoId);
}
