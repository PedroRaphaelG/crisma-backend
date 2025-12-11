package br.edu.uniesp.crisma.repository;

import br.edu.uniesp.crisma.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findByMunicipio(String municipio);
    List<Endereco> findByEstado(String estado);
}
