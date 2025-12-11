package br.edu.uniesp.crisma.controller;

import br.edu.uniesp.crisma.entity.Evento;
import br.edu.uniesp.crisma.enums.TipoEvento;
import br.edu.uniesp.crisma.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
@Tag(name = "Eventos", description = "Endpoints para gerenciamento de eventos")
public class EventoController {

    private final EventoService eventoService;

    @PostMapping
    @Operation(summary = "Criar novo evento")
    public ResponseEntity<Evento> criar(@Valid @RequestBody Evento evento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.criar(evento));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar evento por ID")
    public ResponseEntity<Evento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.buscarPorId(id));
    }

    @GetMapping("/igreja/{igrejaId}")
    @Operation(summary = "Listar eventos de uma igreja")
    public ResponseEntity<List<Evento>> listarPorIgreja(@PathVariable Long igrejaId) {
        return ResponseEntity.ok(eventoService.listarPorIgreja(igrejaId));
    }

    @GetMapping("/grupo/{grupoId}")
    @Operation(summary = "Listar eventos de um grupo")
    public ResponseEntity<List<Evento>> listarPorGrupo(@PathVariable Long grupoId) {
        return ResponseEntity.ok(eventoService.listarPorGrupo(grupoId));
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Listar eventos por tipo")
    public ResponseEntity<List<Evento>> listarPorTipo(@PathVariable TipoEvento tipo) {
        return ResponseEntity.ok(eventoService.listarPorTipo(tipo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar evento")
    public ResponseEntity<Evento> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Evento evento) {
        return ResponseEntity.ok(eventoService.atualizar(id, evento));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar evento")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
