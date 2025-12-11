package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Evento;
import br.edu.uniesp.crisma.enums.TipoEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByIgrejaId(Long igrejaId);
    List<Evento> findByGrupoId(Long grupoId);
    List<Evento> findByTipoEvento(TipoEvento tipoEvento);
    List<Evento> findByDataInicioBetween(LocalDateTime inicio, LocalDateTime fim);
    List<Evento> findByObrigatorioTrue();
}
