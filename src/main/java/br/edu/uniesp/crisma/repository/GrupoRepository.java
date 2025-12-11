package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Grupo;
import br.edu.uniesp.crisma.enums.StatusGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    List<Grupo> findByIgrejaId(Long igrejaId);
    List<Grupo> findByStatus(StatusGrupo status);
    List<Grupo> findByAnoFormacao(Integer anoFormacao);
    List<Grupo> findByIgrejaIdAndStatus(Long igrejaId, StatusGrupo status);
}
