package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Crismando;
import br.edu.uniesp.crisma.enums.StatusCrismando;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrismandoRepository extends JpaRepository<Crismando, Long> {
    Optional<Crismando> findByPessoaId(Long pessoaId);
    List<Crismando> findByGrupoId(Long grupoId);
    List<Crismando> findByStatus(StatusCrismando status);
    List<Crismando> findByGrupoIdAndStatus(Long grupoId, StatusCrismando status);
    
    @Query("SELECT c FROM Crismando c WHERE c.grupo.id = :grupoId AND c.status = 'ATIVO'")
    List<Crismando> findCrismandosAtivosDoGrupo(Long grupoId);
}
