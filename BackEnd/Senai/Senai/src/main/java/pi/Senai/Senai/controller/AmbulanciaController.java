package pi.Senai.Senai.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pi.Senai.Senai.entity.dto.AmbulanciaRequestDTO;
import pi.Senai.Senai.entity.dto.AmbulanciaResponseDTO;
import pi.Senai.Senai.service.AmbulanciaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ambulancias")
public class AmbulanciaController {

    private final AmbulanciaService service;

    public AmbulanciaController(AmbulanciaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AmbulanciaResponseDTO> create(@Valid @RequestBody AmbulanciaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<AmbulanciaResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmbulanciaResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AmbulanciaResponseDTO> update(@PathVariable UUID id, @Valid @RequestBody AmbulanciaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}