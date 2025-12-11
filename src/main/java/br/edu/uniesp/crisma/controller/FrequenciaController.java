package br.edu.uniesp.crisma.controller;

import br.edu.uniesp.crisma.entity.Crismando;
import br.edu.uniesp.crisma.entity.Frequencia;
import br.edu.uniesp.crisma.service.FrequenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/frequencias")
@RequiredArgsConstructor
@Tag(name = "Frequências", description = "Endpoints para gerenciamento de frequência")
public class FrequenciaController {

    private final FrequenciaService frequenciaService;

    @PostMapping
    @Operation(summary = "Registrar frequência")
    public ResponseEntity<Frequencia> registrar(@RequestBody Map<String, Object> request) {
        Long crismandoId = Long.valueOf(request.get("crismandoId").toString());
        Long eventoId = Long.valueOf(request.get("eventoId").toString());
        Boolean presente = (Boolean) request.get("presente");
        String justificativa = (String) request.get("justificativa");

        Frequencia frequencia = frequenciaService.registrar(crismandoId, eventoId, presente, justificativa);
        return ResponseEntity.status(HttpStatus.CREATED).body(frequencia);
    }

    @GetMapping("/crismando/{crismandoId}")
    @Operation(summary = "Listar frequências de um crismando")
    public ResponseEntity<List<Frequencia>> listarPorCrismando(@PathVariable Long crismandoId) {
        return ResponseEntity.ok(frequenciaService.listarPorCrismando(crismandoId));
    }

    @GetMapping("/evento/{eventoId}")
    @Operation(summary = "Listar frequências de um evento")
    public ResponseEntity<List<Frequencia>> listarPorEvento(@PathVariable Long eventoId) {
        return ResponseEntity.ok(frequenciaService.listarPorEvento(eventoId));
    }

    @GetMapping("/crismando/{crismandoId}/percentual")
    @Operation(summary = "Calcular percentual de presença")
    public ResponseEntity<Map<String, Double>> calcularPercentual(@PathVariable Long crismandoId) {
        double percentual = frequenciaService.calcularPercentualPresenca(crismandoId);
        return ResponseEntity.ok(Map.of("percentualPresenca", percentual));
    }

    @GetMapping("/grupo/{grupoId}/relatorio")
    @Operation(summary = "Gerar relatório de frequência do grupo")
    public ResponseEntity<Map<Long, Double>> gerarRelatorio(@PathVariable Long grupoId) {
        return ResponseEntity.ok(frequenciaService.gerarRelatorioGrupo(grupoId));
    }

    @GetMapping("/grupo/{grupoId}/baixa-frequencia")
    @Operation(summary = "Listar crismandos com baixa frequência")
    public ResponseEntity<List<Crismando>> listarBaixaFrequencia(@PathVariable Long grupoId) {
        return ResponseEntity.ok(frequenciaService.listarComBaixaFrequencia(grupoId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar frequência")
    public ResponseEntity<Frequencia> atualizar(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {
        Boolean presente = (Boolean) request.get("presente");
        String justificativa = (String) request.get("justificativa");
        return ResponseEntity.ok(frequenciaService.atualizar(id, presente, justificativa));
    }
}
