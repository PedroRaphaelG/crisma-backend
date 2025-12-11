package br.edu.uniesp.crisma.controller;

import br.edu.uniesp.crisma.entity.Igreja;
import br.edu.uniesp.crisma.service.IgrejaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/igrejas")
@RequiredArgsConstructor
@Tag(name = "Igrejas", description = "Endpoints para gerenciamento de igrejas")
public class IgrejaController {

    private final IgrejaService igrejaService;

    @PostMapping
    @Operation(summary = "Cadastrar nova igreja")
    public ResponseEntity<Igreja> criar(@Valid @RequestBody Igreja igreja) {
        return ResponseEntity.status(HttpStatus.CREATED).body(igrejaService.criar(igreja));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar igreja por ID")
    public ResponseEntity<Igreja> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(igrejaService.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar igrejas ativas")
    public ResponseEntity<List<Igreja>> listarAtivas() {
        return ResponseEntity.ok(igrejaService.listarAtivas());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar igreja")
    public ResponseEntity<Igreja> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Igreja igreja) {
        return ResponseEntity.ok(igrejaService.atualizar(id, igreja));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar igreja")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        igrejaService.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
