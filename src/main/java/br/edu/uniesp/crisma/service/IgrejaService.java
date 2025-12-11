package br.edu.uniesp.crisma.service;

import br.edu.uniesp.crisma.entity.Igreja;
import br.edu.uniesp.crisma.repository.IgrejaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IgrejaService {

    private final IgrejaRepository igrejaRepository;

    @Transactional
    public Igreja criar(Igreja igreja) {
        log.info("Criando nova igreja: {}", igreja.getNome());
        
        if (igrejaRepository.existsByCnpj(igreja.getCnpj())) {
            throw new IllegalArgumentException("CNPJ já cadastrado");
        }
        
        return igrejaRepository.save(igreja);
    }

    @Transactional(readOnly = true)
    public Igreja buscarPorId(Long id) {
        return igrejaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Igreja não encontrada com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Igreja> listarAtivas() {
        return igrejaRepository.findByAtivoTrue();
    }

    @Transactional
    public Igreja atualizar(Long id, Igreja igrejaAtualizada) {
        Igreja igreja = buscarPorId(id);
        igreja.setNome(igrejaAtualizada.getNome());
        igreja.setTelefone(igrejaAtualizada.getTelefone());
        igreja.setEmail(igrejaAtualizada.getEmail());
        igreja.setPadreResponsavel(igrejaAtualizada.getPadreResponsavel());
        return igrejaRepository.save(igreja);
    }

    @Transactional
    public void desativar(Long id) {
        Igreja igreja = buscarPorId(id);
        igreja.setAtivo(false);
        igrejaRepository.save(igreja);
    }
}
