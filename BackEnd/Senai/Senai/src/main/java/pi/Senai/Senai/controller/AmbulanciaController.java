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

import pi.Senai.Senai.entity.Ambulancia;
import pi.Senai.Senai.service.AmbulanciaService;

@RestController
@RequestMapping("/ambulancia")
public class AmbulanciaController {

    @Autowired
    private AmbulanciaService _ambulanciaService;

    @PostMapping("/salvar")
    public void SalvarAmbulancia(@RequestBody Ambulancia ambulancia) {
        _ambulanciaService.SalvarAmbulancia(ambulancia);
    }

    @PutMapping("/atualizar")
    public void AtualizarAmbulancia(@RequestBody Ambulancia ambulancia) {
        _ambulanciaService.AtualizarAmbulancia(ambulancia);
    }

    @DeleteMapping("/excluir/{id}")
    public void ExcluirAmbulancia(@PathVariable UUID id) {
        _ambulanciaService.ExcluirAmbulancia(id);
    }

    @GetMapping("/listar")
    public List<Ambulancia> ListarAmbulancias() {
        return _ambulanciaService.ListarAmbulancias();
    }
}