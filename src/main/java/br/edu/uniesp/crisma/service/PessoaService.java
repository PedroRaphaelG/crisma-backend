package br.edu.uniesp.crisma.service;

import br.edu.uniesp.crisma.entity.Pessoa;
import br.edu.uniesp.crisma.enums.TipoPessoa;
import br.edu.uniesp.crisma.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Transactional
    public Pessoa criar(Pessoa pessoa) {
        log.info("Criando nova pessoa: {}", pessoa.getNome());
        
        if (pessoaRepository.existsByCpf(pessoa.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema");
        }
        
        if (pessoaRepository.existsByEmail(pessoa.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado no sistema");
        }
        
        return pessoaRepository.save(pessoa);
    }

    @Transactional(readOnly = true)
    public Pessoa buscarPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada com ID: " + id));
    }

    @Transactional(readOnly = true)
    public Pessoa buscarPorCpf(String cpf) {
        return pessoaRepository.findByCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada com CPF: " + cpf));
    }

    @Transactional(readOnly = true)
    public List<Pessoa> listarAtivos() {
        return pessoaRepository.findByAtivoTrue();
    }

    @Transactional(readOnly = true)
    public List<Pessoa> listarPorTipo(TipoPessoa tipo) {
        return pessoaRepository.findByTipoPessoa(tipo);
    }

    @Transactional
    public Pessoa atualizar(Long id, Pessoa pessoaAtualizada) {
        log.info("Atualizando pessoa ID: {}", id);
        
        Pessoa pessoa = buscarPorId(id);
        
        // Valida se email está sendo alterado e já não existe
        if (!pessoa.getEmail().equals(pessoaAtualizada.getEmail()) && 
            pessoaRepository.existsByEmail(pessoaAtualizada.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado no sistema");
        }
        
        pessoa.setNome(pessoaAtualizada.getNome());
        pessoa.setEmail(pessoaAtualizada.getEmail());
        pessoa.setTelefone(pessoaAtualizada.getTelefone());
        pessoa.setDataNascimento(pessoaAtualizada.getDataNascimento());
        
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public void desativar(Long id) {
        log.info("Desativando pessoa ID: {}", id);
        Pessoa pessoa = buscarPorId(id);
        pessoa.setAtivo(false);
        pessoaRepository.save(pessoa);
    }

    @Transactional
    public void reativar(Long id) {
        log.info("Reativando pessoa ID: {}", id);
        Pessoa pessoa = buscarPorId(id);
        pessoa.setAtivo(true);
        pessoaRepository.save(pessoa);
    }
}
