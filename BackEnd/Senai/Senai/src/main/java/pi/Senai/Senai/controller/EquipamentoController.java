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

import pi.Senai.Senai.entity.Equipamento;
import pi.Senai.Senai.service.EquipamentoService;

@RestController
@RequestMapping("/equipamento")
public class EquipamentoController {

    @Autowired
    private EquipamentoService equipamentoService;

    @PostMapping("/salvar")
    public void salvar(@RequestBody Equipamento equipamento) {
        equipamentoService.salvar(equipamento);
    }

    @PutMapping("/atualizar")
    public void atualizar(@RequestBody Equipamento equipamento) {
        equipamentoService.atualizar(equipamento);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable UUID id) {
        equipamentoService.excluir(id);
    }

    @GetMapping("/listar")
    public List<Equipamento> listar() {
        return equipamentoService.listar();
    }

    @GetMapping("/nome/{nome}")
    public List<Equipamento> buscarPorNome(@PathVariable String nome) {
        return equipamentoService.buscarPorNome(nome);
    }
}
