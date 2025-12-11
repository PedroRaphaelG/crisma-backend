package br.edu.uniesp.crisma.service;

import br.edu.uniesp.crisma.entity.Crismando;
import br.edu.uniesp.crisma.entity.Grupo;
import br.edu.uniesp.crisma.entity.Pessoa;
import br.edu.uniesp.crisma.enums.StatusCrismando;
import br.edu.uniesp.crisma.enums.TipoPessoa;
import br.edu.uniesp.crisma.repository.CrismandoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrismandoService {

    private final CrismandoRepository crismandoRepository;
    private final PessoaService pessoaService;
    private final GrupoService grupoService;

    @Transactional
    public Crismando inscrever(Pessoa pessoa, Long grupoId, String primeiroResponsavel, 
                               String segundoResponsavel, String telefoneResponsavel) {
        log.info("Inscrevendo novo crismando: {}", pessoa.getNome());
        
        Grupo grupo = grupoService.buscarPorId(grupoId);
        
        // Valida faixa etária
        int idade = pessoa.getIdade();
        if (idade < grupo.getFaixaEtariaMinima() || idade > grupo.getFaixaEtariaMaxima()) {
            throw new IllegalArgumentException(
                String.format("Idade do crismando (%d anos) não está na faixa etária do grupo (%d-%d anos)",
                    idade, grupo.getFaixaEtariaMinima(), grupo.getFaixaEtariaMaxima())
            );
        }
        
        // Define tipo de pessoa como CRISMANDO
        pessoa.setTipoPessoa(TipoPessoa.CRISMANDO);
        Pessoa pessoaSalva = pessoaService.criar(pessoa);
        
        // Cria o crismando
        Crismando crismando = new Crismando();
        crismando.setPessoa(pessoaSalva);
        crismando.setGrupo(grupo);
        crismando.setPrimeiroResponsavel(primeiroResponsavel);
        crismando.setSegundoResponsavel(segundoResponsavel);
        crismando.setTelefoneResponsavel(telefoneResponsavel);
        crismando.setDataInscricao(LocalDate.now());
        crismando.setStatus(StatusCrismando.ATIVO);
        
        return crismandoRepository.save(crismando);
    }

    @Transactional(readOnly = true)
    public Crismando buscarPorId(Long id) {
        return crismandoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Crismando não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Crismando> listarPorGrupo(Long grupoId) {
        return crismandoRepository.findByGrupoId(grupoId);
    }

    @Transactional(readOnly = true)
    public List<Crismando> listarAtivosDoGrupo(Long grupoId) {
        return crismandoRepository.findCrismandosAtivosDoGrupo(grupoId);
    }

    @Transactional(readOnly = true)
    public boolean verificarAptidao(Long crismandoId) {
        Crismando crismando = buscarPorId(crismandoId);
        return crismando.isAptoParaCrisma();
    }

    @Transactional
    public Crismando crismar(Long crismandoId, LocalDate dataCrisma) {
        log.info("Crismando ID {} recebeu o sacramento", crismandoId);
        
        Crismando crismando = buscarPorId(crismandoId);
        
        if (!crismando.isAptoParaCrisma()) {
            throw new IllegalStateException(
                String.format("Crismando não está apto para receber o sacramento. Presença: %.2f%%",
                    crismando.getPercentualPresenca())
            );
        }
        
        crismando.setStatus(StatusCrismando.CRISMADO);
        crismando.setDataCrisma(dataCrisma);
        
        return crismandoRepository.save(crismando);
    }

    @Transactional
    public Crismando marcarDesistente(Long crismandoId) {
        log.info("Marcando crismando ID {} como desistente", crismandoId);
        
        Crismando crismando = buscarPorId(crismandoId);
        crismando.setStatus(StatusCrismando.DESISTENTE);
        
        return crismandoRepository.save(crismando);
    }

    @Transactional
    public Crismando transferir(Long crismandoId, Long novoGrupoId) {
        log.info("Transferindo crismando ID {} para grupo ID {}", crismandoId, novoGrupoId);
        
        Crismando crismando = buscarPorId(crismandoId);
        Grupo novoGrupo = grupoService.buscarPorId(novoGrupoId);
        
        crismando.setGrupo(novoGrupo);
        crismando.setStatus(StatusCrismando.TRANSFERIDO);
        
        return crismandoRepository.save(crismando);
    }

    @Transactional
    public Crismando atualizar(Long id, Crismando crismandoAtualizado) {
        log.info("Atualizando crismando ID: {}", id);
        
        Crismando crismando = buscarPorId(id);
        
        crismando.setPrimeiroResponsavel(crismandoAtualizado.getPrimeiroResponsavel());
        crismando.setSegundoResponsavel(crismandoAtualizado.getSegundoResponsavel());
        crismando.setTelefoneResponsavel(crismandoAtualizado.getTelefoneResponsavel());
        
        return crismandoRepository.save(crismando);
    }
}
