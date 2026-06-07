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

import pi.Senai.Senai.entity.Funcionario;
import pi.Senai.Senai.service.FuncionarioService;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService _funcionarioService;

    @PostMapping("/salvar")
    public void SalvarFuncionario(@RequestBody Funcionario funcionario) {
        _funcionarioService.SalvarFuncionario(funcionario);
    }

    @PutMapping("/atualizar")
    public void AtualizarFuncionario(@RequestBody Funcionario funcionario) {
        _funcionarioService.AtualizarFuncionario(funcionario);
    }

    @DeleteMapping("/excluir/{id}")
    public void ExcluirFuncionario(@PathVariable UUID id) {
        _funcionarioService.ExcluirFuncionario(id);
    }

    @GetMapping("/listar")
    public List<Funcionario> ListarFuncionarios() {
        return _funcionarioService.ListarFuncionarios();
    }
}