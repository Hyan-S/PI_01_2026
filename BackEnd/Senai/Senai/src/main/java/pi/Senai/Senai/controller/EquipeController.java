package pi.Senai.Senai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pi.Senai.Senai.entity.Equipe;
import pi.Senai.Senai.service.EquipeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/equipe")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;

    @PostMapping("/salvar")
    public ResponseEntity<Equipe> salvar(@RequestBody Equipe equipe) {
        Equipe salva = equipeService.salvar(equipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Equipe> atualizar(@RequestBody Equipe equipe) {
        Equipe atualizada = equipeService.atualizar(equipe);
        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        equipeService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Equipe>> listar() {
        return ResponseEntity.ok(equipeService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipe> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(equipeService.buscarPorId(id));
    }

    @GetMapping("/identificador/{identificador}")
    public ResponseEntity<Equipe> buscarPorIdentificador(@PathVariable String identificador) {
        return ResponseEntity.ok(equipeService.buscarPorIdentificador(identificador));
    }
}