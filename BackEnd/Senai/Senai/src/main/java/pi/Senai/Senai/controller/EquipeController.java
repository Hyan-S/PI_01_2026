package pi.Senai.Senai.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pi.Senai.Senai.entity.Equipe;
import pi.Senai.Senai.service.EquipeService;

@RestController
@RequestMapping("/equipe")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;

    @PostMapping("/salvar")
    public Equipe salvar(@RequestBody Equipe equipe) {
        return equipeService.salvar(equipe);
    }

    @PutMapping("/atualizar")
    public void atualizar(@RequestBody Equipe equipe) {
        equipeService.atualizar(equipe);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable UUID id) {
        equipeService.excluir(id);
    }

    @GetMapping("/listar")
    public List<Equipe> listar() {
        return equipeService.listar();
    }

    @GetMapping("/{id}")
    public Equipe buscarPorId(@PathVariable UUID id) {
        return equipeService.buscarPorId(id);
    }

    @GetMapping("/identificador/{identificador}")
    public Equipe buscarPorIdentificador(@PathVariable String identificador) {
        return equipeService.buscarPorIdentificador(identificador);
    }
}
