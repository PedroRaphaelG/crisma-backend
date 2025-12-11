package br.edu.uniesp.crisma.service;

import br.edu.uniesp.crisma.entity.Evento;
import br.edu.uniesp.crisma.enums.TipoEvento;
import br.edu.uniesp.crisma.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventoService {

    private final EventoRepository eventoRepository;

    @Transactional
    public Evento criar(Evento evento) {
        log.info("Criando novo evento: {}", evento.getNome());
        
        if (evento.getDataFim().isBefore(evento.getDataInicio())) {
            throw new IllegalArgumentException("Data de fim deve ser posterior à data de início");
        }
        
        return eventoRepository.save(evento);
    }

    @Transactional(readOnly = true)
    public Evento buscarPorId(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Evento> listarPorIgreja(Long igrejaId) {
        return eventoRepository.findByIgrejaId(igrejaId);
    }

    @Transactional(readOnly = true)
    public List<Evento> listarPorGrupo(Long grupoId) {
        return eventoRepository.findByGrupoId(grupoId);
    }

    @Transactional(readOnly = true)
    public List<Evento> listarPorTipo(TipoEvento tipo) {
        return eventoRepository.findByTipoEvento(tipo);
    }

    @Transactional(readOnly = true)
    public List<Evento> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return eventoRepository.findByDataInicioBetween(inicio, fim);
    }

    @Transactional
    public Evento atualizar(Long id, Evento eventoAtualizado) {
        Evento evento = buscarPorId(id);
        evento.setNome(eventoAtualizado.getNome());
        evento.setDescricao(eventoAtualizado.getDescricao());
        evento.setLocal(eventoAtualizado.getLocal());
        evento.setDataInicio(eventoAtualizado.getDataInicio());
        evento.setDataFim(eventoAtualizado.getDataFim());
        evento.setObrigatorio(eventoAtualizado.getObrigatorio());
        return eventoRepository.save(evento);
    }

    @Transactional
    public void deletar(Long id) {
        eventoRepository.deleteById(id);
    }
}
