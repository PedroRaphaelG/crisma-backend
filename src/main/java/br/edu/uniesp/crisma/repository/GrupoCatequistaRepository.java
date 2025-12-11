package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.GrupoCatequista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoCatequistaRepository extends JpaRepository<GrupoCatequista, Long> {
    List<GrupoCatequista> findByGrupoId(Long grupoId);
    List<GrupoCatequista> findByCatequistaId(Long catequistaId);
    List<GrupoCatequista> findByAtivoTrue();
    
    @Query("SELECT gc FROM GrupoCatequista gc WHERE gc.grupo.id = :grupoId AND gc.ativo = true")
    List<GrupoCatequista> findCatequistasAtivosDoGrupo(Long grupoId);
}
