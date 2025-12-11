package br.edu.uniesp.crisma.service;

import br.edu.uniesp.crisma.entity.Crismando;
import br.edu.uniesp.crisma.entity.Evento;
import br.edu.uniesp.crisma.entity.Frequencia;
import br.edu.uniesp.crisma.repository.FrequenciaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class FrequenciaService {

    private final FrequenciaRepository frequenciaRepository;
    private final CrismandoService crismandoService;
    private final EventoService eventoService;

    @Transactional
    public Frequencia registrar(Long crismandoId, Long eventoId, Boolean presente, String justificativa) {
        log.info("Registrando frequência - Crismando: {}, Evento: {}, Presente: {}", 
                 crismandoId, eventoId, presente);
        
        Crismando crismando = crismandoService.buscarPorId(crismandoId);
        Evento evento = eventoService.buscarPorId(eventoId);
        
        // Verifica se já existe registro
        frequenciaRepository.findByCrismandoIdAndEventoId(crismandoId, eventoId)
                .ifPresent(f -> {
                    throw new IllegalStateException("Frequência já registrada para este crismando neste evento");
                });
        
        Frequencia frequencia = new Frequencia();
        frequencia.setCrismando(crismando);
        frequencia.setEvento(evento);
        frequencia.setPresente(presente);
        frequencia.setJustificativa(justificativa);
        frequencia.setDataRegistro(LocalDate.now());
        
        return frequenciaRepository.save(frequencia);
    }

    @Transactional
    public Frequencia atualizar(Long id, Boolean presente, String justificativa) {
        log.info("Atualizando frequência ID: {}", id);
        
        Frequencia frequencia = frequenciaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Frequência não encontrada com ID: " + id));
        
        frequencia.setPresente(presente);
        frequencia.setJustificativa(justificativa);
        
        return frequenciaRepository.save(frequencia);
    }

    @Transactional(readOnly = true)
    public List<Frequencia> listarPorCrismando(Long crismandoId) {
        return frequenciaRepository.findByCrismandoId(crismandoId);
    }

    @Transactional(readOnly = true)
    public List<Frequencia> listarPorEvento(Long eventoId) {
        return frequenciaRepository.findByEventoId(eventoId);
    }

    @Transactional(readOnly = true)
    public double calcularPercentualPresenca(Long crismandoId) {
        List<Frequencia> frequencias = frequenciaRepository.findByCrismandoId(crismandoId);
        
        if (frequencias.isEmpty()) {
            return 0.0;
        }
        
        long presencas = frequencias.stream().filter(Frequencia::getPresente).count();
        return (presencas * 100.0) / frequencias.size();
    }

    @Transactional(readOnly = true)
    public Map<Long, Double> gerarRelatorioGrupo(Long grupoId) {
        List<Crismando> crismandos = crismandoService.listarPorGrupo(grupoId);
        
        return crismandos.stream()
                .collect(Collectors.toMap(
                    Crismando::getId,
                    c -> calcularPercentualPresenca(c.getId())
                ));
    }

    @Transactional(readOnly = true)
    public List<Crismando> listarComBaixaFrequencia(Long grupoId) {
        List<Crismando> crismandos = crismandoService.listarPorGrupo(grupoId);
        
        return crismandos.stream()
                .filter(c -> calcularPercentualPresenca(c.getId()) < 75.0)
                .collect(Collectors.toList());
    }
}
