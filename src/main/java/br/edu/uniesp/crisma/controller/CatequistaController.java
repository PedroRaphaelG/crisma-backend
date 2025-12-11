package br.edu.uniesp.crisma.controller;

import br.edu.uniesp.crisma.entity.Catequista;
import br.edu.uniesp.crisma.entity.Pessoa;
import br.edu.uniesp.crisma.service.CatequistaService;
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
@RequestMapping("/api/catequistas")
@RequiredArgsConstructor
@Tag(name = "Catequistas", description = "Endpoints para gerenciamento de catequistas")
public class CatequistaController {

    private final CatequistaService catequistaService;

    @PostMapping
    @Operation(summary = "Cadastrar novo catequista")
    public ResponseEntity<Catequista> cadastrar(@RequestBody Map<String, Object> request) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome((String) request.get("nome"));
        pessoa.setCpf((String) request.get("cpf"));
        pessoa.setEmail((String) request.get("email"));
        pessoa.setTelefone((String) request.get("telefone"));
        pessoa.setDataNascimento(LocalDate.parse((String) request.get("dataNascimento")));

        Integer anoCrisma = Integer.valueOf(request.get("anoCrisma").toString());
        String setorTrabalho = (String) request.get("setorTrabalho");
        Integer experienciaAnos = request.containsKey("experienciaAnos")
                ? Integer.valueOf(request.get("experienciaAnos").toString())
                : 0;

        Catequista catequista = catequistaService.cadastrar(pessoa, anoCrisma, setorTrabalho, experienciaAnos);
        return ResponseEntity.status(HttpStatus.CREATED).body(catequista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar catequista por ID")
    public ResponseEntity<Catequista> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(catequistaService.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar catequistas ativos")
    public ResponseEntity<List<Catequista>> listarAtivos() {
        return ResponseEntity.ok(catequistaService.listarAtivos());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar catequista")
    public ResponseEntity<Catequista> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody Catequista catequista) {
        return ResponseEntity.ok(catequistaService.atualizar(id, catequista));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inativar catequista")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        catequistaService.inativar(id);
        return ResponseEntity.noContent().build();
    }
}
