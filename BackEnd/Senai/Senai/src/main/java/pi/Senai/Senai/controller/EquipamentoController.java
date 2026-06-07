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
    private EquipamentoService _equipamentoService;

    @PostMapping("/salvar")
    public void SalvarEquipamento(@RequestBody Equipamento equipamento) {
        _equipamentoService.SalvarEquipamento(equipamento);
    }

    @PutMapping("/atualizar")
    public void AtualizarEquipamento(@RequestBody Equipamento equipamento) {
        _equipamentoService.AtualizarEquipamento(equipamento);
    }

    @DeleteMapping("/excluir/{id}")
    public void ExcluirEquipamento(@PathVariable UUID id) {
        _equipamentoService.ExcluirEquipamento(id);
    }

    @GetMapping("/listar")
    public List<Equipamento> ListarEquipamentos() {
        return _equipamentoService.ListarEquipamentos();
    }
}
