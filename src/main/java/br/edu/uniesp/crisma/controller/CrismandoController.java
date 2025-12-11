package br.edu.uniesp.crisma.controller;

import br.edu.uniesp.crisma.entity.Crismando;
import br.edu.uniesp.crisma.entity.Pessoa;
import br.edu.uniesp.crisma.service.CrismandoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/crismandos")
@RequiredArgsConstructor
@Tag(name = "Crismandos", description = "Endpoints para gerenciamento de crismandos")
public class CrismandoController {

    private final CrismandoService crismandoService;

    @PostMapping
    @Operation(summary = "Inscrever novo crismando", description = "Inscreve um novo crismando no programa de crisma")
    public ResponseEntity<Crismando> inscrever(
            @Valid @RequestBody Map<String, Object> request) {

        // Extrai dados da pessoa
        Pessoa pessoa = new Pessoa();
        pessoa.setNome((String) request.get("nome"));
        pessoa.setCpf((String) request.get("cpf"));
        pessoa.setEmail((String) request.get("email"));
        pessoa.setTelefone((String) request.get("telefone"));
        pessoa.setDataNascimento(LocalDate.parse((String) request.get("dataNascimento")));

        // Extrai dados do crismando
        Long grupoId = Long.valueOf(request.get("grupoId").toString());
        String primeiroResponsavel = (String) request.get("primeiroResponsavel");
        String segundoResponsavel = (String) request.get("segundoResponsavel");
        String telefoneResponsavel = (String) request.get("telefoneResponsavel");

        Crismando crismando = crismandoService.inscrever(
                pessoa, grupoId, primeiroResponsavel, segundoResponsavel, telefoneResponsavel);

        return ResponseEntity.status(HttpStatus.CREATED).body(crismando);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar crismando por ID")
    public ResponseEntity<Crismando> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(crismandoService.buscarPorId(id));
    }

    @GetMapping("/grupo/{grupoId}")
    @Operation(summary = "Listar crismandos de um grupo")
    public ResponseEntity<List<Crismando>> listarPorGrupo(@PathVariable Long grupoId) {
        return ResponseEntity.ok(crismandoService.listarPorGrupo(grupoId));
    }

    @GetMapping("/grupo/{grupoId}/ativos")
    @Operation(summary = "Listar crismandos ativos de um grupo")
    public ResponseEntity<List<Crismando>> listarAtivosDoGrupo(@PathVariable Long grupoId) {
        return ResponseEntity.ok(crismandoService.listarAtivosDoGrupo(grupoId));
    }

    @GetMapping("/{id}/aptidao")
    @Operation(summary = "Verificar aptidão para crisma", description = "Verifica se o crismando tem presença mínima de 75%")
    public ResponseEntity<Map<String, Object>> verificarAptidao(@PathVariable Long id) {
        boolean apto = crismandoService.verificarAptidao(id);
        Crismando crismando = crismandoService.buscarPorId(id);

        return ResponseEntity.ok(Map.of(
                "crismandoId", id,
                "apto", apto,
                "percentualPresenca", crismando.getPercentualPresenca()));
    }

    @PutMapping("/{id}/crismar")
    @Operation(summary = "Marcar crismando como crismado")
    public ResponseEntity<Crismando> crismar(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        LocalDate dataCrisma = LocalDate.parse(request.get("dataCrisma"));
        return ResponseEntity.ok(crismandoService.crismar(id, dataCrisma));
    }

    @PutMapping("/{id}/desistente")
    @Operation(summary = "Marcar crismando como desistente")
    public ResponseEntity<Crismando> marcarDesistente(@PathVariable Long id) {
        return ResponseEntity.ok(crismandoService.marcarDesistente(id));
    }

    @PutMapping("/{id}/transferir")
    @Operation(summary = "Transferir crismando para outro grupo")
    public ResponseEntity<Crismando> transferir(
            @PathVariable Long id,
            @RequestBody Map<String, Long> request) {

        Long novoGrupoId = request.get("novoGrupoId");
        return ResponseEntity.ok(crismandoService.transferir(id, novoGrupoId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do crismando")
    public ResponseEntity<Crismando> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Crismando crismando) {
        return ResponseEntity.ok(crismandoService.atualizar(id, crismando));
    }
}
