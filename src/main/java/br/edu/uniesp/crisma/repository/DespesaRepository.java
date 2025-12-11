package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Despesa;
import br.edu.uniesp.crisma.enums.CategoriaDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByGrupoId(Long grupoId);
    List<Despesa> findByCategoria(CategoriaDespesa categoria);
    List<Despesa> findByDataDespesaBetween(LocalDate inicio, LocalDate fim);
    
    @Query("SELECT SUM(d.valor) FROM Despesa d WHERE d.grupo.id = :grupoId")
    BigDecimal calcularTotalDespesasPorGrupo(Long grupoId);
}
