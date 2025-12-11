package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Frequencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FrequenciaRepository extends JpaRepository<Frequencia, Long> {
    List<Frequencia> findByCrismandoId(Long crismandoId);
    List<Frequencia> findByEventoId(Long eventoId);
    Optional<Frequencia> findByCrismandoIdAndEventoId(Long crismandoId, Long eventoId);
    
    @Query("SELECT f FROM Frequencia f WHERE f.crismando.id = :crismandoId AND f.presente = true")
    List<Frequencia> findPresencasByCrismandoId(Long crismandoId);
    
    @Query("SELECT f FROM Frequencia f WHERE f.crismando.id = :crismandoId AND f.presente = false")
    List<Frequencia> findFaltasByCrismandoId(Long crismandoId);
}
