package br.edu.uniesp.crisma.controller;

import br.edu.uniesp.crisma.entity.Grupo;
import br.edu.uniesp.crisma.enums.StatusGrupo;
import br.edu.uniesp.crisma.service.GrupoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
@Tag(name = "Grupos", description = "Endpoints para gerenciamento de grupos de crisma")
public class GrupoController {

    private final GrupoService grupoService;

    @PostMapping
    @Operation(summary = "Criar novo grupo")
    public ResponseEntity<Grupo> criar(@Valid @RequestBody Grupo grupo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(grupoService.criar(grupo));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar grupo por ID")
    public ResponseEntity<Grupo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(grupoService.buscarPorId(id));
    }

    @GetMapping("/igreja/{igrejaId}")
    @Operation(summary = "Listar grupos de uma igreja")
    public ResponseEntity<List<Grupo>> listarPorIgreja(@PathVariable Long igrejaId) {
        return ResponseEntity.ok(grupoService.listarPorIgreja(igrejaId));
    }

    @GetMapping("/igreja/{igrejaId}/ativos")
    @Operation(summary = "Listar grupos ativos de uma igreja")
    public ResponseEntity<List<Grupo>> listarAtivosDeIgreja(@PathVariable Long igrejaId) {
        return ResponseEntity.ok(grupoService.listarAtivosDeIgreja(igrejaId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar grupos por status")
    public ResponseEntity<List<Grupo>> listarPorStatus(@PathVariable StatusGrupo status) {
        return ResponseEntity.ok(grupoService.listarPorStatus(status));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados do grupo")
    public ResponseEntity<Grupo> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Grupo grupo) {
        return ResponseEntity.ok(grupoService.atualizar(id, grupo));
    }

    @PutMapping("/{id}/concluir")
    @Operation(summary = "Concluir grupo")
    public ResponseEntity<Grupo> concluir(@PathVariable Long id) {
        return ResponseEntity.ok(grupoService.concluir(id));
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar grupo")
    public ResponseEntity<Grupo> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(grupoService.cancelar(id));
    }
}
