package br.edu.uniesp.crisma.service;

import br.edu.uniesp.crisma.entity.Catequista;
import br.edu.uniesp.crisma.entity.Pessoa;
import br.edu.uniesp.crisma.enums.StatusCatequista;
import br.edu.uniesp.crisma.enums.TipoPessoa;
import br.edu.uniesp.crisma.repository.CatequistaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatequistaService {

    private final CatequistaRepository catequistaRepository;
    private final PessoaService pessoaService;

    @Transactional
    public Catequista cadastrar(Pessoa pessoa, Integer anoCrisma, String setorTrabalho, Integer experienciaAnos) {
        log.info("Cadastrando novo catequista: {}", pessoa.getNome());
        
        pessoa.setTipoPessoa(TipoPessoa.CATEQUISTA);
        Pessoa pessoaSalva = pessoaService.criar(pessoa);
        
        Catequista catequista = new Catequista();
        catequista.setPessoa(pessoaSalva);
        catequista.setAnoCrisma(anoCrisma);
        catequista.setSetorTrabalho(setorTrabalho);
        catequista.setExperienciaAnos(experienciaAnos != null ? experienciaAnos : 0);
        catequista.setStatus(StatusCatequista.ATIVO);
        
        return catequistaRepository.save(catequista);
    }

    @Transactional(readOnly = true)
    public Catequista buscarPorId(Long id) {
        return catequistaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Catequista não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Catequista> listarAtivos() {
        return catequistaRepository.findByStatus(StatusCatequista.ATIVO);
    }

    @Transactional
    public Catequista atualizar(Long id, Catequista catequistaAtualizado) {
        Catequista catequista = buscarPorId(id);
        catequista.setSetorTrabalho(catequistaAtualizado.getSetorTrabalho());
        catequista.setExperienciaAnos(catequistaAtualizado.getExperienciaAnos());
        return catequistaRepository.save(catequista);
    }

    @Transactional
    public void inativar(Long id) {
        Catequista catequista = buscarPorId(id);
        catequista.setStatus(StatusCatequista.INATIVO);
        catequistaRepository.save(catequista);
    }
}
