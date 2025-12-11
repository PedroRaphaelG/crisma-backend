package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.ContribuicaoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ContribuicaoPessoaRepository extends JpaRepository<ContribuicaoPessoa, Long> {
    List<ContribuicaoPessoa> findByCaixinhaId(Long caixinhaId);
    List<ContribuicaoPessoa> findByPessoaId(Long pessoaId);
    
    @Query("SELECT SUM(c.valorPago) FROM ContribuicaoPessoa c WHERE c.caixinha.id = :caixinhaId")
    BigDecimal calcularTotalArrecadadoPorCaixinha(Long caixinhaId);
}
