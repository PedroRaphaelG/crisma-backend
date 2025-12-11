package br.edu.uniesp.crisma.service;

import br.edu.uniesp.crisma.entity.Grupo;
import br.edu.uniesp.crisma.entity.Igreja;
import br.edu.uniesp.crisma.enums.StatusGrupo;
import br.edu.uniesp.crisma.repository.GrupoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class GrupoService {

    private final GrupoRepository grupoRepository;
    private final IgrejaService igrejaService;

    @Transactional
    public Grupo criar(Grupo grupo) {
        log.info("Criando novo grupo: {}", grupo.getNome());
        
        // Valida faixa etária
        if (grupo.getFaixaEtariaMinima() >= grupo.getFaixaEtariaMaxima()) {
            throw new IllegalArgumentException("Faixa etária mínima deve ser menor que a máxima");
        }
        
        // Define ano de formação como ano atual se não informado
        if (grupo.getAnoFormacao() == null) {
            grupo.setAnoFormacao(LocalDate.now().getYear());
        }
        
        // Valida igreja
        Igreja igreja = igrejaService.buscarPorId(grupo.getIgreja().getId());
        grupo.setIgreja(igreja);
        
        grupo.setStatus(StatusGrupo.ATIVO);
        
        return grupoRepository.save(grupo);
    }

    @Transactional(readOnly = true)
    public Grupo buscarPorId(Long id) {
        return grupoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grupo não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Grupo> listarPorIgreja(Long igrejaId) {
        return grupoRepository.findByIgrejaId(igrejaId);
    }

    @Transactional(readOnly = true)
    public List<Grupo> listarAtivosDeIgreja(Long igrejaId) {
        return grupoRepository.findByIgrejaIdAndStatus(igrejaId, StatusGrupo.ATIVO);
    }

    @Transactional(readOnly = true)
    public List<Grupo> listarPorStatus(StatusGrupo status) {
        return grupoRepository.findByStatus(status);
    }

    @Transactional
    public Grupo atualizar(Long id, Grupo grupoAtualizado) {
        log.info("Atualizando grupo ID: {}", id);
        
        Grupo grupo = buscarPorId(id);
        
        grupo.setNome(grupoAtualizado.getNome());
        grupo.setFaixaEtariaMinima(grupoAtualizado.getFaixaEtariaMinima());
        grupo.setFaixaEtariaMaxima(grupoAtualizado.getFaixaEtariaMaxima());
        
        return grupoRepository.save(grupo);
    }

    @Transactional
    public Grupo concluir(Long id) {
        log.info("Concluindo grupo ID: {}", id);
        
        Grupo grupo = buscarPorId(id);
        grupo.setStatus(StatusGrupo.CONCLUIDO);
        
        return grupoRepository.save(grupo);
    }

    @Transactional
    public Grupo cancelar(Long id) {
        log.info("Cancelando grupo ID: {}", id);
        
        Grupo grupo = buscarPorId(id);
        grupo.setStatus(StatusGrupo.CANCELADO);
        
        return grupoRepository.save(grupo);
    }
}
