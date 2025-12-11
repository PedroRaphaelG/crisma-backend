package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Formacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FormacaoRepository extends JpaRepository<Formacao, Long> {
    List<Formacao> findByFormadorId(Long formadorId);
    List<Formacao> findByDataFormacaoBetween(LocalDate inicio, LocalDate fim);
    List<Formacao> findByCidade(String cidade);
}
