package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.CrismandoPadrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrismandoPadrinhoRepository extends JpaRepository<CrismandoPadrinho, Long> {
    List<CrismandoPadrinho> findByCrismandoId(Long crismandoId);
    List<CrismandoPadrinho> findByPadrinhoId(Long padrinhoId);
}
